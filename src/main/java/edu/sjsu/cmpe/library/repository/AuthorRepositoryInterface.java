package edu.sjsu.cmpe.library.repository;

import edu.sjsu.cmpe.library.domain.Author;

/**
 * Author repository 
 */

public interface AuthorRepositoryInterface {
    /**
     * Save a new author in the repository
     * 
     * @param newAuthor
     *            an author instance to be create in the repository
     * @return a newly created author instance with auto-generated ID
     */
    Author saveAuthor(Author author);

    /**
     * Retrieve an existing author by ID
     * 
     * @param ID
     *            a valid ID
     * @return an author instance
     */
    Author getAuthorById(Long id);

}