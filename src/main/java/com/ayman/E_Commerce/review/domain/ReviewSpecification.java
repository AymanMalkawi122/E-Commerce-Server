package com.ayman.E_Commerce.review.domain;

import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewFieldNames;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> ratingInRange(Double min, Double max) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (min == 0 && max == 5) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get(ReviewFieldNames.rating), min, max);
        };
    }

    public static Specification<Review> withProductId(Long id) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ReviewFieldNames.productId), id);
        };
    }

    public static Specification<Review> WithUserId(Long id) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ReviewFieldNames.userId), id);
        };
    }
}
