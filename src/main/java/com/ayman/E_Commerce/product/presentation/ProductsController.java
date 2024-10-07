package com.ayman.E_Commerce.product.presentation;

import com.ayman.E_Commerce.core.Constants;
import com.ayman.E_Commerce.product.domain.product.ProductSpecification;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.product.infrastructure.product.ProductsService;
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
@RequestMapping("product")
@Validated
public class ProductsController {
    final ProductsService service;

    @Autowired
    ProductsController(ProductsService service) {
        this.service = service;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product product) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.createProduct(product, user).toResponseEntity();
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "1") @Positive int page,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        Specification<Product> spec = Specification.anyOf(ProductSpecification.titleContains(title));
        return service.getProducts(spec, page - 1, size).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @Positive Long id) {
        return service.getProductById(id).toResponseEntity();
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('" + Constants.USER_ROLE_NAME + "')")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.updateProduct(product, user).toResponseEntity();
        //TODO prevent user from updating rating value
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('" + Constants.USER_ROLE_NAME +  "','"+ Constants.ADMIN_ROLE_NAME +")")
    public ResponseEntity<String> DeleteProductById(@PathVariable @Positive Long id) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.deleteProductById(id, user).toResponseEntity();
    }
}
