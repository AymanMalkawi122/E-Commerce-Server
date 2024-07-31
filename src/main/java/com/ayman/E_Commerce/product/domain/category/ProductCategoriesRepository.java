package com.ayman.E_Commerce.product.domain.category;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import com.ayman.E_Commerce.product.infrastructure.category.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductCategoriesRepository extends BaseRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByName(String name);

    default ProductCategory throwIfInvalid(ProductCategory category) {
        final Optional<String> categoryName = Optional.ofNullable(category).map(ProductCategory::getName);
        final Optional<ProductCategory> result = categoryName.flatMap(this::findByName);

        if(result.isEmpty() && categoryName.isPresent()) {
            throw new RepositoryException("the following Category: " + categoryName.get() +" is invalid", HttpStatus.BAD_REQUEST);
        }
        return result.orElse(category);
    }

    default void throwIfExistsByName(String name, RepositoryException cause) {
        final Optional<String> categoryName = Optional.ofNullable(name);
        final Optional<ProductCategory> result = categoryName.flatMap(this::findByName);

        if(result.isPresent()) {
            throw cause;
        }
    }
}