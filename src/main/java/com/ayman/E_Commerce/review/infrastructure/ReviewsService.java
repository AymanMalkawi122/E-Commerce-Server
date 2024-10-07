package com.ayman.E_Commerce.review.infrastructure;

import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.user.infrastructure.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReviewsService {
    ResponseState<Review> createReview(Review review, User user);
    ResponseState<List<Review>> getReviews(Specification<Review> spec, int page, int size);
    ResponseState<Optional<Review>> getReviewById(Long id, boolean throwIfNull);
    ResponseState<Review> updateReview(Review review, User user);
    ResponseState<String> deleteReviewById(Long id, User user);
}
