package com.ayman.E_Commerce.review.domain;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.review.infrastructure.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends BaseRepository<Review, Long> {
}
