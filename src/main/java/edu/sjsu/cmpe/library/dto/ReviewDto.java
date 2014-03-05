package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {
    private List<Review> reviews;

    public ReviewDto()
    {
	super();
	reviews = new ArrayList<Review>();
    }

    public ReviewDto(Review review)
    {
	super();
	reviews = new ArrayList<Review>();
	addReview(review);
    }

    public ReviewDto(List<Review> reviews)
    {
	super();
	this.reviews = reviews;
    }

    public List<Review> getReview()
    {
	return reviews;
    }

    public void addReview(Review review)
    {
	reviews.add(review);
    }
}
