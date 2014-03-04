package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
    private long id;
    
    @JsonProperty(required = true)
    private String name;

    public long getId()
    {
	return id;
    }

    public void setId(long id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }
}