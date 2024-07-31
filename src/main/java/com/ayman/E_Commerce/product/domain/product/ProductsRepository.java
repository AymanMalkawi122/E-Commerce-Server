package com.ayman.E_Commerce.product.domain.product;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends BaseRepository<Product, Long> {
}
