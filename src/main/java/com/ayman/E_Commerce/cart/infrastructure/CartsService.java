package com.ayman.E_Commerce.cart.infrastructure;

import com.ayman.E_Commerce.core.ResponseState;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface CartsService {
    ResponseState<Cart> createCart(Cart review);
    ResponseState<List<Cart>> getCarts(Specification<Cart> spec, int page, int size);
    ResponseState<Optional<Cart>> getCartById(Long id);
    ResponseState<Cart> updateCart(Cart review);
    ResponseState<String> deleteCartById(Long id);
}
