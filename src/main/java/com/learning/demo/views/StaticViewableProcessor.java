package com.learning.demo.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.internal.TemplateHelper;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;

import org.springframework.stereotype.Component;

@Component
@Provider
public class StaticViewableProcessor implements TemplateProcessor<Reader> {

	private static final Map<String, MediaType> MEDIA_TYPE_MAPPING = new HashMap<String, MediaType>() {
		{
			put("html", new MediaType("text","html"));
			put("js", new MediaType("application", "javascript"));
			put("css", new MediaType("text", "css"));
		}};

	private final String basePath;
	private final ServletContext servletContext;
	private final Charset encoding;

	@Inject
	public StaticViewableProcessor(Configuration configuration, @org.jvnet.hk2.annotations.Optional ServletContext servletContext) {
		this.servletContext = servletContext;
		this.basePath = getBasePath(configuration);
		this.encoding = TemplateHelper.getTemplateOutputEncoding(configuration, null);
	}

	private String getBasePath(Configuration configuration) {
		String basePath = PropertiesHelper.getValue(configuration.getProperties(), MvcFeature.TEMPLATE_BASE_PATH, String.class, null);
		if (basePath == null) {
			basePath = PropertiesHelper.getValue(configuration.getProperties(), MvcFeature.TEMPLATE_BASE_PATH, "", null);
		}
		basePath = StringUtils.prependIfMissing(basePath, "/");
		basePath = StringUtils.removeEnd(basePath, "/");
		return basePath;
	}

	@Override
	public Reader resolve(String name, MediaType mediaType) {
		String path = basePath + name;
		// ServletContext.
		Reader reader = Optional.ofNullable(servletContext)
				.map(servletContext -> servletContext.getResourceAsStream(path))
				.map(InputStreamReader::new)
				.orElse(null);

		// Classloader.
		if (reader == null) {
			InputStream stream = Optional.ofNullable(getClass().getResourceAsStream(path))
					.orElseGet(() -> getClass().getClassLoader().getResourceAsStream(path));
			reader = Optional.ofNullable(stream)
					.map(InputStreamReader::new)
					.orElse(null);
		}

		// File-system path.
		if (reader == null) {
			try {
				reader = new InputStreamReader(new FileInputStream(path), encoding);
			} catch (FileNotFoundException e) {
				// NOP
			}
		}

		return reader;
	}

	@Override
	public void writeTo(Reader templateReference, Viewable viewable, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException {
		MediaType effectiveMediaType;
		MediaType fileMediaType = getFileMediaType(viewable.getTemplateName());
		if (fileMediaType != null && MediaTypes.isWildcard(mediaType)) {
			effectiveMediaType = new MediaType(fileMediaType.getType(), fileMediaType.getSubtype(), mediaType.getParameters());
		} else {
			effectiveMediaType = mediaType;
		}

		httpHeaders.putSingle(HttpHeaders.CONTENT_TYPE, effectiveMediaType);

		String charset = effectiveMediaType.getParameters().get(MediaType.CHARSET_PARAMETER);
		try {
			IOUtils.copy(templateReference, out, charset);
		} finally {
			templateReference.close();
		}
	}

	private MediaType getFileMediaType(String fileName) {
		MediaType mediaType = null;
		String extension = FilenameUtils.getExtension(fileName);
		if (!extension.isEmpty()) {
			mediaType = MEDIA_TYPE_MAPPING.get(extension);
		}
		return mediaType;
	}
}
