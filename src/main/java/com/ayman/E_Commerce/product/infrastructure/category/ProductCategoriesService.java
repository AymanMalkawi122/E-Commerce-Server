package com.ayman.E_Commerce.product.infrastructure.category;

import com.ayman.E_Commerce.core.ResponseState;

import java.util.List;
import java.util.Optional;

public interface ProductCategoriesService {
    ResponseState<ProductCategory> createProductCategory(ProductCategory category);
    ResponseState<List<ProductCategory>> getProductCategories(int page, int size);
    ResponseState<Optional<ProductCategory>> getProductCategoryById(Long id);
    ResponseState<ProductCategory> updateProductCategory(ProductCategory category);
    ResponseState<String> deleteProductCategoryById(Long id);
}

