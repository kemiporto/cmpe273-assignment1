package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class Review {
    private long id;

    @JsonProperty(required = true)
    @Min(value = 1, message = "rating must be at least 1")
    @Max(value = 5, message = "rating must be at most 5")
    @NotNull(message = "Rating")
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