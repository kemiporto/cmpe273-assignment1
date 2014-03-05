package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;

import java.util.List;

@JsonPropertyOrder(alphabetic = true)

public class AuthorDtoSingleElement extends LinksDto {
    private List<Author> authors;
    
    public AuthorDto(List<Author> authors)
    {
	super();
	this.authors = authors;
    }

    public Author getAuthor()
    {
	return author;
    }

    public void setAuthors(List<Author> authors)
    {
	this.authors = authors;
    }
}