package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.*;

@JsonPropertyOrder({"id", "rating", "comment"})
public class Review {
    private long id;

    @JsonProperty(required = true)
    @NotNull(message = "Rating must not be null")
    @Min(value = 1, message = "rating must be at least 1")
    @Max(value = 5, message = "rating must be at most 5")
    private int rating;

    @JsonProperty
    @NotNull(message = "Comment must be specified. \"comment\" : \"your comment\"")
    private String comment;
 
    public void setId(Long id) {
	this.id = id;
    }   

    public Long getId() {
	return id;
    }
}