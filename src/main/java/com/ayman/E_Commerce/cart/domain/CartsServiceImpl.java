package com.ayman.E_Commerce.cart.domain;

import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.cart.infrastructure.CartsService;
import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.product.infrastructure.product.ProductsService;
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
    private final ProductsService productsService;

    @Autowired
    public CartsServiceImpl(CartsRepository cartsRepository, ProductsService productsService) {
        this.cartsRepository = cartsRepository;
        this.productsService = productsService;
    }

    @Override
    public ResponseState<Cart> createCart(Cart review) {
        final boolean productExists = productsService.existsById(review.getProductId()).getData();
        if(!productExists) {
            throw new EntityNotFoundException(BaseRepository.entityNotFoundMessage("Product", review.getProductId().toString()));
        }
        //TODO check if user is valid
        return new ResponseState<>(cartsRepository.save(review), HttpStatus.CREATED);
    }

    @Override
    public ResponseState<List<Cart>> getCarts(Specification<Cart> spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(cartsRepository.findAll(spec, pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Optional<Cart>> getCartById(Long id) {
        final Optional<Cart> result = cartsRepository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException(BaseRepository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Cart> updateCart(Cart review) {
        Cart result = cartsRepository.ifExistsElseThrow(review.getId(), () -> cartsRepository.save(review));
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteCartById(Long id) {
        cartsRepository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
