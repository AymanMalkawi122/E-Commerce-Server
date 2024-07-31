package com.ayman.E_Commerce.product.domain.product;

import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.product.infrastructure.product.ProductFieldNames;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

    public static Specification<Product> titleContains(String title) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get(ProductFieldNames.title), "%" + title + "%");
        };
    }
}
