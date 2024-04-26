package com.learning.demo.rest;

import com.learning.demo.rest.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/demo/v1/book")
public class BookResource {

	private static final Logger log = LoggerFactory.getLogger(BookResource.class);

	@GET
	@Path ("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Book Resource", description = "Method to get All Books", tags = {"Endpoints"})  // you can create diff tag for diff controller example: "Book" as the tag, but if you do you need to add it into @OpenAPIDefinition
	public List<Book> getAllBooks() {
		List<Book> bookList = new ArrayList<>();
		Book book1 = new Book();
		book1.setId(101);
		book1.setTitle("Dragons of Autumn Twilight");
		book1.setDescription("It is based on a series of Dungeons & Dragons (D&D) game modules.  It was the first Dragonlance novel, and first in the Chronicles trilogy, which, along with the Dragonlance Legends trilogy, are generally regarded as the core novels of the Dragonlance world.");
		List<String> authors = new ArrayList<>();
		authors.add("Margaret Weis");
		authors.add("Tracy Hickman");
		book1.setAuthors(authors);

		Book book2 = new Book();
		book2.setId(102);
		book2.setTitle("Dragons of Winter Night");
		book2.setDescription("It is the second book in the Chronicles Trilogy");
		List<String> authors2 = new ArrayList<>();
		authors2.add("Margaret Weis");
		authors2.add("Tracy Hickman");
		book2.setAuthors(authors2);

		bookList.add(book1);
		bookList.add(book2);
		return bookList;
	}

}
