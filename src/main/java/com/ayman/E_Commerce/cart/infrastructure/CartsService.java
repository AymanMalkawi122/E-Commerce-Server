package com.ayman.E_Commerce.cart.infrastructure;

import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.user.infrastructure.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CartsService {
    ResponseState<Cart> createCart(Cart review, User user);
    ResponseState<List<Cart>> getCarts(int page, int size, Specification<Cart> spec, User user);
    ResponseState<Cart> getCartById(Long id, User user);
    ResponseState<Cart> updateCart(Cart cart, User user);
    ResponseState<Cart> addProduct(Long productId, Long cartId, User user);
    ResponseState<Cart> removeProduct(Long productId, Long cartId, User user);
    ResponseState<String> deleteCartById(Long id, User user);
}
