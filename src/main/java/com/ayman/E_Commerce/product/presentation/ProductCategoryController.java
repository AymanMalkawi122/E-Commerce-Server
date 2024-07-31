package com.ayman.E_Commerce.product.presentation;

import com.ayman.E_Commerce.product.infrastructure.category.ProductCategoriesService;
import com.ayman.E_Commerce.product.infrastructure.category.ProductCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product/category")
@Validated
public class ProductCategoryController {
    final ProductCategoriesService service;

    @Autowired
    public ProductCategoryController(ProductCategoriesService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<ProductCategory> createNewProductCategory(@RequestBody @Valid ProductCategory category) {
        return service.createProductCategory(category).toResponseEntity();
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories(
            @RequestParam(defaultValue = "1") @Positive int page,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        return service.getProductCategories(page - 1, size).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductCategory>> getProductCategoryById(@PathVariable @Positive Long id) {
        return service.getProductCategoryById(id).toResponseEntity();
    }

    @PutMapping("/")
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody @Valid ProductCategory category) {
        return service.updateProductCategory(category).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteProductCategoryById(@PathVariable @Positive Long id) {
        return service.deleteProductCategoryById(id).toResponseEntity();
    }

}
