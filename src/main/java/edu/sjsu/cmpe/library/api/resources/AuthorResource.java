package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.repository.AuthorRepositoryInterface;

@Path("/v1/books/{isbn}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthorResource {
    private final AuthorRepositoryInterface authorRepository;

    public AuthorResource(AuthorRepositoryInterface authorRepository)
    {
	this.authorRepository = authorRepository;
    } 

    @GET
    @Path("/{id}")
    @Timed(name = "view-author")
	public AuthorDto getAuthorById(@PathParam("id") LongParam id, @PathParam("isbn") LongParam isbn)
    {
	Author author = authorRepository. getAuthorById(id.get());
	AuthorDto authorResponse = new AuthorDto(author);

	authorResponse.addLink(new LinkDto("view-author", "/books/" + isbn.get() + "/authors/" + id.get(), "GET"));

	return authorResponse;
    }
}