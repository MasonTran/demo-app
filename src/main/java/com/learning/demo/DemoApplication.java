package com.learning.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition(
		info = @Info(
				title = "Demo Application",
				description = "Demo Application with Swagger.",
				contact = @Contact(name = "Company Name - Division", email = "mason.tran@yahoo.com"),
				version = "0.0.1",
				termsOfService = "https://www.google.com"

		),
		tags = {
				@Tag(name = "Endpoints", description = "Application Endpoints"),
				@Tag(name = "default", description = "Default Library Endpoints"),
				@Tag(name = "Utility", description="Common AppUtility Endpoints")
		}
)
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application
				.sources(DemoApplication.class);
	}

}
