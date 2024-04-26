package com.learning.demo.config;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		this.registerEndpoints();
		register(JacksonFeature.class);
		//Swagger Resources -- Jersey Configs
		register(MvcFeature.class);
		property(MvcFeature.TEMPLATE_BASE_PATH,"");
		//Open api 3.0 -- jersey config
		register(OpenApiResource.class);
		register(AcceptHeaderOpenApiResource.class);
	}

	private void registerEndpoints() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));

		this.registerClasses(scanner.findCandidateComponents("com.learning.demo.rest").stream()
				.map(beanDefinition -> ClassUtils.resolveClassName(Objects.requireNonNull(beanDefinition.getBeanClassName()), this.getClassLoader()))
				.collect(Collectors.toSet()));

		this.registerClasses(scanner.findCandidateComponents("com.learning.demo.views").stream()
				.map(beanDefinition -> ClassUtils.resolveClassName(Objects.requireNonNull(beanDefinition.getBeanClassName()), this.getClassLoader()))
				.collect(Collectors.toSet()));
	}
}
