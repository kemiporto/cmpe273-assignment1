package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;

import java.util.List;

@JsonPropertyOrder(alphabetic = true)

public class AuthorDtoMultipleElement extends LinksDto {
    private List<Author> authors;
    
    public AuthorDtoMultipleElement(List<Author> authors)
    {
	super();
	this.authors = authors;
    }

    public List<Author> getAuthors()
    {
	return authors;
    }

    public void setAuthors(List<Author> authors)
    {
	this.authors = authors;
    }
}