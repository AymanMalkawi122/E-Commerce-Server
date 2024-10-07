package com.ayman.E_Commerce.cart.presentation;

import com.ayman.E_Commerce.cart.domain.CartSpecification;
import com.ayman.E_Commerce.cart.infrastructure.Cart;
import com.ayman.E_Commerce.cart.infrastructure.CartsService;
import com.ayman.E_Commerce.core.Constants;
import com.ayman.E_Commerce.user.infrastructure.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
@Validated
public class CartsController {
    final CartsService service;

    @Autowired
    CartsController(CartsService service) {
        this.service = service;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Cart> createNewCart(@RequestBody @Valid Cart cart) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.createCart(cart, user).toResponseEntity();
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('" + Constants.ADMIN_ROLE_NAME + "')")
    public ResponseEntity<List<Cart>> getAllCarts(
            @RequestParam(required = false) @Positive Long userId,
            @RequestParam(value = "page", defaultValue = "1") @Positive int page,
            @RequestParam(value = "size", defaultValue = "10") @Positive int size
    ) {
        Specification<Cart> spec = Specification.anyOf(CartSpecification.hasUserId(userId));
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getCarts(page - 1, size, spec, user).toResponseEntity();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('" + Constants.USER_ROLE_NAME +  "','"+ Constants.ADMIN_ROLE_NAME +")")
    public ResponseEntity<Cart> getCartById(@PathVariable @Positive Long id) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getCartById(id, user).toResponseEntity();
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Cart> updateCart(@RequestBody @Valid Cart cart) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.updateCart(cart, user).toResponseEntity();
    }

    @PatchMapping("/add_product")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Cart> addProduct(
            @PathVariable long productId,
            @PathVariable long cartId
    ) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.addProduct(productId, cartId, user).toResponseEntity();
    }

    @PatchMapping("/remove_product")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Cart> removeProduct(
            @PathVariable long productId,
            @PathVariable long cartId
    ) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.removeProduct(productId, cartId, user).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<String> DeleteCartById(@PathVariable @Positive Long id) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.deleteCartById(id, user).toResponseEntity();
    }
}
