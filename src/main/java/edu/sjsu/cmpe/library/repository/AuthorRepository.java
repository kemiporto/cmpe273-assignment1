package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorRepository implements AuthorRepositoryInterface{
    private final ConcurrentHashMap<Long, Author> authorInMemoryMap;
    private final ConcurrentHashMap<String, Long> nameInMemoryMap;

    private long id;

    public AuthorRepository(ConcurrentHashMap<Long, Author> authorMap)
    {
	checkNotNull(authorMap, "authorMap must not be null for AuthorRepository");
	authorInMemoryMap = authorMap;
	nameInMemoryMap = new ConcurrentHashMap<String, Long>();
	id = 0;
	for(int i = 0; i < authorMap.size(); ++i) {
	    Author a = authorMap.get(i);
	    nameInMemoryMap.putIfAbsent(a.getName(), a.getId());
	}
    }

    private final Long generateId()
    {
	return Long.valueOf(++id);
    }

    @Override
    public Author saveAuthor(Author newAuthor)
    {
	checkNotNull(newAuthor, "newAuthor instance must not be null");
	if(nameInMemoryMap.get(newAuthor.getName()) == null) {
	    newAuthor.setId(generateId());
	    authorInMemoryMap.put(newAuthor.getId(), newAuthor);
	    nameInMemoryMap.put(newAuthor.getName(), newAuthor.getId());
	}
	return newAuthor;
    }

    @Override
    public Author getAuthorById(Long id)
    {
	checkArgument(id > 0, "id was %d but was expected greater than 0 value", id);
	return authorInMemoryMap.get(id);
    }
}