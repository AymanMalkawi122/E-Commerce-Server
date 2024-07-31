package com.ayman.E_Commerce.cart.domain;

import com.ayman.E_Commerce.cart.infrastructure.Cart;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CartSpecification {

    public static Specification<Cart> titleContains(String title) {
        return (Root<Cart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }
}
