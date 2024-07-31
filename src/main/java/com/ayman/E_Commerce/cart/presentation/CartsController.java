package com.ayman.E_Commerce.cart.presentation;

import com.ayman.E_Commerce.cart.domain.CartSpecification;
import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.cart.infrastructure.CartFieldNames;
import com.ayman.E_Commerce.cart.infrastructure.CartsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("review")
@Validated
public class CartsController {
    final CartsService service;

    @Autowired
    CartsController(CartsService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Cart> createNewReview(@RequestBody @Valid Cart review) {
        return service.createCart(review).toResponseEntity();
    }

    @GetMapping("/")
    public ResponseEntity<List<Cart>> getAllReviews(
            @RequestParam(value = CartFieldNames.userId, required = false) @Positive Long userId,
            @RequestParam(value = CartFieldNames.id, required = false) @Positive Long id,
            @RequestParam(value = "page", defaultValue = "1") @Positive int page,
            @RequestParam(value = "size", defaultValue = "10") @Positive int size
    ) {
        Specification<Cart> spec = Specification.anyOf(CartSpecification.titleContains("title"));
        return service.getCarts(spec, page - 1, size).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cart>> getReviewById(@PathVariable @Positive Long id) {
        return service.getCartById(id).toResponseEntity();
    }

    @PutMapping("/")
    public ResponseEntity<Cart> updateReview(@RequestBody @Valid Cart review) {
        return service.updateCart(review).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteReviewById(@PathVariable @Positive Long id) {
        return service.deleteCartById(id).toResponseEntity();
    }
}
