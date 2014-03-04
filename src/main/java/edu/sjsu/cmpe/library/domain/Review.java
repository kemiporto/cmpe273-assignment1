package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class Review {
    private long id;
    @JsonProperty(required = true)
	@Min(1)
	@Max(5)
	private int rating;
    
}