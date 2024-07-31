package com.ayman.E_Commerce.product.infrastructure.product;

import com.ayman.E_Commerce.core.ResponseState;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    ResponseState<Product> createProduct(Product product);
    ResponseState<List<Product>> getProducts(Specification<Product> spec, int page, int size);
    ResponseState<Optional<Product>> getProductById(Long id);
    ResponseState<Boolean> existsById(Long id);
    ResponseState<Product> updateProduct(Product product);
    ResponseState<String> deleteProductById(Long id);
}
