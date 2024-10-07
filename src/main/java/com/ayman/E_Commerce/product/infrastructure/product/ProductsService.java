package com.ayman.E_Commerce.product.infrastructure.product;

import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.user.infrastructure.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductsService {
    ResponseState<Product> createProduct(Product product, User user);
    ResponseState<List<Product>> getProducts(Specification<Product> spec, int page, int size);
    ResponseState<Product> getProductById(Long id);
    ResponseState<Boolean> existsById(Long id);
    ResponseState<Product> updateProduct(Product product, User user);
    ResponseState<String> deleteProductById(Long id, User user);
}
