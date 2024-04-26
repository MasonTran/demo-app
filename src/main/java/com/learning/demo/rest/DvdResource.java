package com.learning.demo.rest;

import com.learning.demo.rest.model.Dvd;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/demo/v1/dvd")
public class DvdResource {

	private static final Logger log = LoggerFactory.getLogger(DvdResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Dvd Summary", description = "Method to get Dvd Summary", tags = {"Endpoints"})  // you can create diff tag for diff controller example: "Dvd" as the tag, but if you do you need to add it into @OpenAPIDefinition
	public Dvd getDvd() {
		final Dvd dvd = new Dvd();
		return dvd;
	}

}
