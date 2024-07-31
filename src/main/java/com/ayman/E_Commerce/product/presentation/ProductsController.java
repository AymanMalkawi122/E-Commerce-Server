package com.ayman.E_Commerce.product.presentation;

import com.ayman.E_Commerce.product.domain.product.ProductSpecification;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.product.infrastructure.product.ProductFieldNames;
import com.ayman.E_Commerce.product.infrastructure.product.ProductsService;
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
@RequestMapping("product")
@Validated
public class ProductsController {
    final ProductsService service;

    @Autowired
    ProductsController(ProductsService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product product) {
        return service.createProduct(product).toResponseEntity();
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
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable @Positive Long id) {
        return service.getProductById(id).toResponseEntity();
    }

    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        return service.updateProduct(product).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteProductById(@PathVariable @Positive Long id) {
        return service.deleteProductById(id).toResponseEntity();
    }
}
