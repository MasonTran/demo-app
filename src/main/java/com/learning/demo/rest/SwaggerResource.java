package com.learning.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/swagger")
@Component
public class SwaggerResource {

	@Context
	UriInfo uriInfo;


	@Operation(summary = "Loads the Swagger UI Page", description = "Method to load swagger-ui page", tags = {"Utility"})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns the index.html file of the Swagger-UI")})
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getSwaggerUi() {
		String requestPath = uriInfo.getRequestUri().getPath();
		Response response;
		if (!requestPath.endsWith("/")) {
			response = Response.temporaryRedirect(URI.create(requestPath + "/")).build(); // redirect for relative urls
		} else {
			response = Response.ok(new Viewable("/swagger-ui/index.html"))
					.header("Cache-Control","public,max-age=86400,s-max-age=86400")
					.build();
		}
		return response;
	}

	@Operation(summary = "Loads the Images,CSS & JS required", description = "Method to load swagger-ui page", tags = {"Utility"})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns the resources required for the Swagger-UI")})
	@GET
	@Path("{fileName:.*\\.(css|js|png|html|map)}")
	public Response getFiles(@PathParam("fileName") String fileName) {
		return Response.ok(new Viewable("/swagger-ui/" + fileName))
				.header("Cache-Control","public,max-age=86400,s-max-age=86400")
				.build();
	}
}
