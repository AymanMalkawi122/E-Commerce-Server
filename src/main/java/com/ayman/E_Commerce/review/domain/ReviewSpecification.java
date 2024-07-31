package com.ayman.E_Commerce.review.domain;

import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewFieldNames;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> titleContains(String title) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }
}
