package com.learning.demo.rest;

import java.lang.management.ManagementFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.stereotype.Component;

@Path("")
@Component
public class DemoAppDashboard {

	private static final String HEALTH_ENDPOINT = "/actuator/health";
	private static final String ACTUATOR_ENDPOINT = "/actuator";
	private static final String ACTUATOR_ENV = "/actuator/env";
	private static final String OPENAPI_ENDPOINT = "/openapi.json";
	private static final String SWAGGER_ENDPOINT = "/swagger";
	private static final String ROOT_PAGE_TEMPLATE =
			"<html>" +
					"<title>%s Root Page</title>" +
					"<body>" +
					"<div><a href='%s'>UP</a> - %s seconds</div>" +
					"<div><a href='%s'>Actuator Endpoints</a></div>" +
					"<div><a href='%s'>Actuator Environment</a></div>" +
					"<div><a href='%s'>OpenAPI Spec</a></div>" +
					"<div><a href='%s'>Swagger UI</a></div>" +
					"</body>" +
					"</html>";

	@Operation(summary = "Demo App Welcome Page", description = "HealthCheck Endpoint", tags = {"Utility"})
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response healthCheck() {
		final long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
		return Response.status(200)
				.entity(String.format(ROOT_PAGE_TEMPLATE,"demo-app", HEALTH_ENDPOINT, uptime / 1000,
						ACTUATOR_ENDPOINT, ACTUATOR_ENV, OPENAPI_ENDPOINT, SWAGGER_ENDPOINT))
				.encoding("UTF-8")
				.build();
	}
}
