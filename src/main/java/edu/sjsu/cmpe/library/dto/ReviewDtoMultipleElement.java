package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder({"reviews", "links"})
public class ReviewDtoMultipleElement extends LinksDto {
    private List<Review> reviews;

    public ReviewDtoMultipleElement(List<Review> reviews)
    {
	super();
	this.reviews = reviews;
    }

    public List<Review> getReviews()
    {
	return reviews;
    }

    public void setReviews(List<Review> review)
    {
	this.reviews = reviews;
    }
}
