package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.BookResponse;

public class BookResponseDto extends LinksDto {
    private BookResponse book;

    /**
     * @param book
     */
    public BookResponseDto(BookResponse book) {
	super();
	this.book = book;
    }

    /**
     * @return the book
     */
    public BookResponse getBook() {
	return book;
    }

    /**
     * @param book
     *            the book to set
     */
    public void setBook(BookResponse book) {
	this.book = book;
    }
}
