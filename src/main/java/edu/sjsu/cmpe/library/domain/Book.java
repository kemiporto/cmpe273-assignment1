package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cmpe.library.domain.Review;
import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.*;

public class Book {
    private long isbn;

    private static String AVAILABLE = "available";
    private static String CHECKED_OUT = "checked-out";
    private static String IN_QUEUE = "in-queue";
    private static String LOST = "lost";

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
    private String status = Book.AVAILABLE;
    @JsonProperty (required = true)
    @NotNull(message = "must have a defined authors value.\n\"authors\" : \n\t[{\"name\" : \"author_1's name\"},..., {\"name\" : \"author_n's name\"}].")
    private List<Author> authors;
    @JsonProperty
    private List<Review> reviews = null;
    
    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    public String getPublicationDate()
    {
	return publicationDate;
    }

    public String getLanguage()
    {
	return language;
    }

    public Integer getNPages()
    {
	return nPages;
    }

    public void setStatus(String status)
    {
	this.status = status;
    }

    public String getStatus()
    {
	return status;
    }
    
    public boolean hasReview()
    {
	return (reviews != null && reviews.size() > 0);
    }

    public void addReview(Review review) {
	if(reviews == null)
	    reviews = new ArrayList<Review>();
	reviews.add(review);
    }

    public List<Review> getReviews() {
	return reviews;
    }

    public List<Author> getAuthors() {
	return authors;
    }

    public Author getAuthor(int position) {
	return authors.get(position);
    }
}
