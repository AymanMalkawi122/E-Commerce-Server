package com.ayman.E_Commerce.cart.domain;

import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends BaseRepository<Cart, Long> {
    @Override
    default String entityName() {
        return "Cart";
    }
}
