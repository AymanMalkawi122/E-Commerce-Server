package com.ayman.E_Commerce.review.presentation;

import com.ayman.E_Commerce.core.Constants;
import com.ayman.E_Commerce.review.domain.ReviewSpecification;
import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewsService;
import com.ayman.E_Commerce.user.infrastructure.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Review> createNewReview(@RequestBody @Valid Review review) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.createReview(review, user).toResponseEntity();
    }

    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllReviews(
            @RequestParam(required = false) @DecimalMin("0.0") @DecimalMax("5.0") Double ratingMax,
            @RequestParam(required = false) @DecimalMin("0.0") @DecimalMax("5.0") Double ratingMin,
            @RequestParam(required = false) @Positive Long productId,
            @RequestParam(required = false) @Positive Long userId,
            @RequestParam(defaultValue = "1") @Positive int page,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        Specification<Review> spec = Specification.allOf(
                ReviewSpecification.ratingInRange(
                        ratingMin.isNaN() ? 0 : ratingMin,
                        ratingMax.isNaN() ? 5 : ratingMax
                ),
                ReviewSpecification.withProductId(productId),
                ReviewSpecification.WithUserId(userId)
        );
        return service.getReviews(spec, page - 1, size).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getReviewById(@PathVariable @Positive Long id) {
        return service.getReviewById(id, true).toResponseEntity();
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Review> updateReview(@RequestBody @Valid Review review) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.updateReview(review, user).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('" + Constants.USER_ROLE_NAME +  "','"+ Constants.ADMIN_ROLE_NAME +")")
    public ResponseEntity<String> DeleteReviewById(@PathVariable @Positive Long id) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.deleteReviewById(id, user).toResponseEntity();
    }

}
