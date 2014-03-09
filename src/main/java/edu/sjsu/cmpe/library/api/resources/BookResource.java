package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import static com.google.common.base.Preconditions.checkNotNull;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.BookResponseDto;
import edu.sjsu.cmpe.library.domain.BookResponse;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dto.AuthorDtoSingleElement;
import edu.sjsu.cmpe.library.dto.AuthorDtoMultipleElement;
import edu.sjsu.cmpe.library.repository.AuthorRepositoryInterface;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.ReviewDtoSingleElement;
import edu.sjsu.cmpe.library.dto.ReviewDtoMultipleElement;
import edu.sjsu.cmpe.library.repository.ReviewRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private final AuthorRepositoryInterface authorRepository;
    private final ReviewRepositoryInterface reviewRepository;

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
    public BookResource(BookRepositoryInterface bookRepository, AuthorRepositoryInterface authorRepository,
			ReviewRepositoryInterface reviewRepository) 
    {
	this.bookRepository = bookRepository;
	this.authorRepository = authorRepository;
	this.reviewRepository = reviewRepository;
    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookResponseDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	checkNotNull(book, "there is no book with isbn %d", isbn.get());
	BookResponseDto bookResponse = new BookResponseDto(new BookResponse(book));

	String bookLocation =  "/books/" + book.getIsbn();
	bookResponse.addLink(new LinkDto("view-book", bookLocation, GET));
	bookResponse.addLink(new LinkDto("update-book", bookLocation, PUT));
	bookResponse.addLink(new LinkDto("delete-book", bookLocation, DELETE));
	bookResponse.addLink(new LinkDto("create-review", bookLocation + "/reviews", POST));
	if(book.hasReview())
	{
	    bookResponse.addLink(new LinkDto("view-all-reviews", bookLocation + "/reviews", GET));
	}
	return bookResponse;
    }

    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public Response viewReviewById(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam id)
    {
	Review review = reviewRepository.getReviewById(id.get());
	ReviewDtoSingleElement reviewResponse = new ReviewDtoSingleElement(review);
	Book book = bookRepository.getBookByISBN(isbn.get());

	reviewResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + id.get(), GET));
	return Response.status(200).entity(reviewResponse).build();
    }

    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-reviews")
    public Response viewAllReviews(@PathParam("isbn") LongParam isbn)
    {
	Book book = bookRepository.getBookByISBN(isbn.get());
	ReviewDtoMultipleElement reviewResponse = new ReviewDtoMultipleElement(book.getReviews());

	return Response.status(200).entity(reviewResponse).build();
    }

    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-author")
    public Response viewAuthor(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam id)
    {
	Author author = authorRepository.getAuthorById(id.get());

	AuthorDtoSingleElement authorResponse = new AuthorDtoSingleElement(author);
	Book book = bookRepository.getBookByISBN(isbn.get());

	authorResponse.addLink(new LinkDto("view-author", "/book/" + book.getIsbn() + "/authors/" + author.getId(), GET));
	return Response.status(200).entity(authorResponse).build();
    }

    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-authors")
    public Response viewAuthors(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	AuthorDtoMultipleElement authorResponse = new AuthorDtoMultipleElement(book.getAuthors());

	return Response.status(200).entity(authorResponse).build();
    }

    @POST
    @Timed(name = "create-book")
    public Response createBook(@Valid Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	for(int i = 0; i < request.getAuthors().size(); ++i) {
	    request.setAuthor(i, authorRepository.saveAuthor(request.getAuthor(i)));
	}
	String location = "/books/" + savedBook.getIsbn();
	LinksDto bookResponse = new LinksDto();
	bookResponse.addLink(new LinkDto("view-book", location, GET));
	bookResponse.addLink(new LinkDto("update-book", location, PUT));
	bookResponse.addLink(new LinkDto("delete-book", location, DELETE));
	bookResponse.addLink(new LinkDto("create-review", location + "/reviews", POST));

	return Response.status(201).entity(bookResponse).build();
    }

    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(@Valid Review review, @PathParam("isbn") LongParam isbn)
    {
	Book book = bookRepository.getBookByISBN(isbn.get());
	checkNotNull(book, "isbn not existent");
	reviewRepository.saveReview(review);
	book.addReview(review);

	LinksDto reviewResponse = new LinksDto();
	reviewResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + review.getId(), GET));
	return Response.status(201).entity(reviewResponse).build();
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

	public Response updateBook(@PathParam("isbn") LongParam isbn, @QueryParam("status") Book.Status newStatus)
    {
	Book book = bookRepository.getBookByISBN(isbn.get());
	book.setStatus(newStatus);
	LinksDto bookResponse = new LinksDto();

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

