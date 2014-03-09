package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.domain.Book;
import java.util.List;
import javax.validation.constraints.*;
import java.util.ArrayList;

@JsonPropertyOrder({"isbn", "title", "publication-date", "language", "num-pages", "status", "reviews", "author"})
public class BookResponse {
    @JsonProperty
    private long isbn;

    private static String GET = "GET";

    @JsonProperty(required=true)
    @NotNull(message = "must have a defined title value. \n\"title\" : \"book title\".")
    private String title;
    @JsonProperty(required=true, value="publication-date")
    @NotNull (message = "must have a defined publication-date value. \n\"publication-date\" : \"mm/dd/yyyy\".")
    private String publicationDate;
    @JsonProperty
    private String language;
    @JsonProperty(value="num-pages")
    private Integer nPages;
    @JsonProperty
    private String status;
    @JsonProperty (required = true)
    @NotNull(message = "must have a defined authors value.\n\"authors\" : \n\t[{\"name\" : \"author_1's name\"},..., {\"name\" : \"author_n's name\"}].")
    private List<LinkDto> authors;
    @JsonProperty
    private List<LinkDto> reviews;
    
    public BookResponse(Book book)
    {
	isbn = book.getIsbn();
	title = book.getTitle();
	publicationDate = book.getPublicationDate();
	language = book.getLanguage();
	nPages = book.getNPages();
	status = book.getStatus();
	
	authors = new ArrayList<LinkDto>();
	reviews = new ArrayList<LinkDto>();

	for(int i = 0; i < book.getAuthors().size(); ++i)
	{
	    authors.add(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + book.getAuthor(i).getId(), GET));
	}

	if(book.hasReview())
	{
	    for(int i = 0; i < book.getReviews().size(); ++i)
	    {
		reviews.add(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + book.getReview(i).getId(), GET));
	    }
	}
    }

}
