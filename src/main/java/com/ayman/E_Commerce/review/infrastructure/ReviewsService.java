package com.ayman.E_Commerce.review.infrastructure;

import com.ayman.E_Commerce.core.ResponseState;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReviewsService {
    ResponseState<Review> createReview(Review review);
    ResponseState<List<Review>> getReviews(Specification<Review> spec, int page, int size);
    ResponseState<Optional<Review>> getReviewById(Long id);
    ResponseState<Review> updateReview(Review review);
    ResponseState<String> deleteReviewById(Long id);
}
