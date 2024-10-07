package com.ayman.E_Commerce.cart.domain;

import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.cart.infrastructure.CartsService;
import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.core.UtilMethods;
import com.ayman.E_Commerce.product.domain.product.ProductsRepository;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.user.infrastructure.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartsServiceImpl implements CartsService {

    private final CartsRepository cartsRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public CartsServiceImpl(CartsRepository cartsRepository, ProductsRepository productsService) {
        this.cartsRepository = cartsRepository;
        this.productsRepository = productsService;
    }

    @Override
    public ResponseState<Cart> createCart(Cart cart, User user) {
        UtilMethods.throwIfNotOwner(user, cart.getUserId());
        return new ResponseState<>(
                cartsRepository.ifIsNewElseThrow(
                        cart.getId(),
                        () -> cartsRepository.save(cart)
                ), HttpStatus.CREATED);
    }

    @Override
    public ResponseState<List<Cart>> getCarts(int page, int size, Specification<Cart> spec, User user) {
        UtilMethods.throwIfNotAdmin(user);
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(cartsRepository.findAll(spec, pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Cart> getCartById(Long id, User user) {
        final Cart result = cartsRepository.ifExistsElseThrow(id, (cart) -> {
            UtilMethods.throwIfNotOwnerOrAdmin(user, cart.getUserId());
            return cart;
        });
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Cart> updateCart(Cart cart, User user) {
        Cart result = cartsRepository.ifExistsElseThrow(
                cart.getId(),
                () -> {
                    UtilMethods.throwIfNotOwnerOrAdmin(user, cart.getUserId());
                    return cartsRepository.save(cart);
                }
        );
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Cart> addProduct(Long productId, Long cartId, User user) {
        final Optional<Product> product = productsRepository.findById(productId);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(productsRepository.entityNotFoundMessage(productId.toString()));
        }
        final Cart result = cartsRepository.ifExistsElseThrow(
                cartId,
                (cart) -> {
                    UtilMethods.throwIfNotOwner(user, cart.getUserId());
                    cart.addProduct(product.get());
                    return cart;
                }
        );
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Cart> removeProduct(Long productId, Long cartId, User user) {
        final Cart result = cartsRepository.ifExistsElseThrow(
                cartId,
                (cart) -> {
                    UtilMethods.throwIfNotOwner(user, cart.getUserId());
                    cart.removeProduct(productId);
                    return cart;
                }
        );
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteCartById(Long id, User user) {
        cartsRepository.ifExistsElseThrow(id, (cart) -> {
            UtilMethods.throwIfNotOwner(user, cart.getUserId());
            cartsRepository.deleteById(id);
            return null;
        });
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
