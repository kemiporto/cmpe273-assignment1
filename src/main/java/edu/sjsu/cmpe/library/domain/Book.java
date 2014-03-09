package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.domain.Review;
import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.*;

@JsonPropertyOrder({"isbn", "title", "publication-date", "language", "num-pages", "status", "reviews", "authors"})
public class Book {
    private long isbn;

    public static enum Status {
	available("available"),
	checked_out("checked-out"),
	in_queue("in-queue"),
        lost("lost");
	private final String text;

	private Status(final String text) {
	    this.text = text;
	}

	@Override
	public String toString() {
	    return text;
	}

	@JsonCreator
	public static Status forValue(String value) {
	    for (Status s : Status.values()) {
		if (s.toString().compareToIgnoreCase(value) == 0) {
		    return s;
		}
	    }
	    throw new IllegalArgumentException("'" + value + "' is not a valid status.");
	}
    }

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
    private Status status = Status.available;
    @JsonProperty (required = true)
    @NotNull(message = "must have a defined authors value.\n\"authors\" : \n\t[{\"name\" : \"author_1's name\"},..., {\"name\" : \"author_n's name\"}].")
    private List<Author> authors;
    @Null(message = "to create review use create-review. Not allowed on the creation of book")
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
	return language.toLowerCase();
    }

    public Integer getNPages()
    {
	return nPages;
    }

    public void setStatus(Status status)
    {
	this.status = status;
    }

    public String getStatus()
    {
	return status.toString();
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

    public Review getReview(int position)
    {
	return reviews.get(position);
    }

    public List<Author> getAuthors() {
	return authors;
    }

    public Author getAuthor(int position) {
	return authors.get(position);
    }

    public void setAuthor(int position, Author author) {
	authors.set(position, author);
    }
}
