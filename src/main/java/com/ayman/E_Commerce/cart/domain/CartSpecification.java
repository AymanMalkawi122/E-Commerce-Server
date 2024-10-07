package com.ayman.E_Commerce.cart.domain;

import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.cart.infrastructure.CartFieldNames;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CartSpecification {

    public static Specification<Cart> hasUserId(Long userId) {
        return (Root<Cart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(CartFieldNames.userId), "%" + userId + "%");
        };
    }
}
