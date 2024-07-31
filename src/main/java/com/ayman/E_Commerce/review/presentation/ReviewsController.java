package com.ayman.E_Commerce.review.presentation;

import com.ayman.E_Commerce.review.domain.ReviewSpecification;
import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewFieldNames;
import com.ayman.E_Commerce.review.infrastructure.ReviewsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("review")
@Validated
public class ReviewsController {
    final ReviewsService service;

    @Autowired
    ReviewsController(ReviewsService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Review> createNewReview(@RequestBody @Valid Review review) {
        return service.createReview(review).toResponseEntity();
    }

    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllReviews(
            @RequestParam(value = ReviewFieldNames.rating, required = false) @DecimalMin("0.0") @DecimalMax("5.0") Double rating,
            @RequestParam(value = ReviewFieldNames.userId, required = false) @Positive Long userId,
            @RequestParam(value = ReviewFieldNames.id, required = false) @Positive Long id,
            @RequestParam(value = "page", defaultValue = "1") @Positive int page,
            @RequestParam(value = "size", defaultValue = "10") @Positive int size
    ) {
        Specification<Review> spec = Specification.anyOf(ReviewSpecification.titleContains("title"));
        return service.getReviews(spec, page - 1, size).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getReviewById(@PathVariable @Positive Long id) {
        return service.getReviewById(id).toResponseEntity();
    }

    @PutMapping("/")
    public ResponseEntity<Review> updateReview(@RequestBody @Valid Review review) {
        return service.updateReview(review).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteReviewById(@PathVariable @Positive Long id) {
        return service.deleteReviewById(id).toResponseEntity();
    }

}
