package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Review;

public class ReviewRepository implements ReviewRepositoryInterface{
    private final ConcurrentHashMap<Long, Review> reviewInMemoryMap;
    private long id;

    public ReviewRepository(ConcurrentHashMap<Long, Review> reviewMap)
    {
	checkNotNull(reviewMap, "reviewMap must not be null for ReviewRepository");
	reviewInMemoryMap = reviewMap;
	id = 0;
    }

    private final Long generateId() {
	return Long.valueOf(++id);
    }

    @Override
    public Review saveReview(Review review) {
	checkNotNull(review, "review instance must no be null");
	review.setId(generateId());
	reviewInMemoryMap.put(review.getId(), review);
	return review;
    }

    @Override
    public Review getReviewById(Long id) {
	checkArgument(id > 0, "id was %d but was expected greater than 0 value", id);
	return reviewInMemoryMap.get(id);
    }
}