package edu.sjsu.cmpe.library.repository;

import edu.sjsu.cmpe.library.domain.Review;

public interface ReviewRepositoryInterface {

    Review saveReview(Review review);

    Review getReviewById(Long id);
}