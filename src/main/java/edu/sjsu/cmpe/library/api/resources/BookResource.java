package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import static com.google.common.base.Preconditions.checkNotNull;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.repository.AuthorRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private final AuthorRepositoryInterface authorRepository;

    private static String POST = "POST";
    private static String GET = "GET";
    private static String DELETE = "DELETE";
    private static String PUT = "PUT";

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository, AuthorRepositoryInterface authorRepository) {
	this.bookRepository = bookRepository;
	this.authorRepository = authorRepository;
    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	checkNotNull(book, "there is no book with isbn %d", isbn.get());
	BookDto bookResponse = new BookDto(book);

	String bookLocation =  "/books/" + book.getIsbn();
	bookResponse.addLink(new LinkDto("view-book", bookLocation, GET));
	bookResponse.addLink(new LinkDto("update-book", bookLocation, POST));
	bookResponse.addLink(new LinkDto("delete-book", bookLocation, DELETE));
	bookResponse.addLink(new LinkDto("create-review", bookLocation + "/reviews", POST));
	if(book.hasReview())
	{
	    bookResponse.addLink(new LinkDto("view-all-reviews", bookLocation + "/reviews", GET));
	}
	return bookResponse;
    }

    @POST
    @Timed(name = "create-book")
	public Response createBook(@Valid Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	/*	for(int i = 0; i < request.getAuthors().size(); ++i) {
	    authorRepository.saveAuthor(request.getAuthor(i));
	    }*/

	String location = "/books/" + savedBook.getIsbn();
	BookDto bookResponse = new BookDto(savedBook);
	bookResponse.addLink(new LinkDto("view-book", location, GET));
	bookResponse.addLink(new LinkDto("update-book", location, POST));
	bookResponse.addLink(new LinkDto("delete-book", location, DELETE));
	bookResponse.addLink(new LinkDto("create-review", location, POST));

	return Response.status(201).entity(bookResponse).build();
    }

    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn)
    {
	bookRepository.deleteBookByISBN(isbn.get());

	LinksDto response = new LinksDto();
	response.addLink(new LinkDto("create-book", "/books", POST));

	return Response.status(200).entity(response).build();
    }

    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")

    public Response updateBook(@PathParam("isbn") LongParam isbn)
    {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);

	String location = "/books/" + book.getIsbn();
	bookResponse.addLink(new LinkDto("view-book", location, GET));
	bookResponse.addLink(new LinkDto("update-book", location, PUT));
	bookResponse.addLink(new LinkDto("delete-book", location, DELETE));
	bookResponse.addLink(new LinkDto("create-review", location + "/reviews", POST));
	if(book.hasReview())
	    bookResponse.addLink(new LinkDto("view-all-reviews", location + "/reviews", GET));

	return Response.status(200).entity(bookResponse).build();
    }
}

