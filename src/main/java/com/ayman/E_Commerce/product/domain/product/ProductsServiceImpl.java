package com.ayman.E_Commerce.product.domain.product;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.core.UtilMethods;
import com.ayman.E_Commerce.product.domain.category.ProductCategoriesRepository;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.product.infrastructure.product.ProductsService;
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
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductCategoriesRepository categoriesRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository, ProductCategoriesRepository productCategoriesRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = productCategoriesRepository;
    }

    @Override
    public ResponseState<Product> createProduct(Product product, User user) {
        UtilMethods.throwIfNotOwner(user, product.getOwnerId());
        product.setCategory(categoriesRepository.throwIfInvalid(product.getCategory()));
        return new ResponseState<>(productsRepository.save(product), HttpStatus.CREATED);
    }

    @Override
    public ResponseState<List<Product>> getProducts(Specification<Product> spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(productsRepository.findAll(spec, pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Product> getProductById(Long id) {
        final Optional<Product> result = productsRepository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException(productsRepository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result.get(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Boolean> existsById(Long id) {
        return new ResponseState<>(productsRepository.existsById(id), HttpStatus.OK);
    }

    @Override
    public ResponseState<Product> updateProduct(Product product, User user) {
        UtilMethods.throwIfNotOwner(user, product.getOwnerId());
        product.setCategory(categoriesRepository.throwIfInvalid(product.getCategory()));
        Product result = productsRepository.ifExistsElseThrow(product.getId(), () -> productsRepository.save(product));
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteProductById(Long id, User user) {
        Product product = getProductById(id).getData();
        UtilMethods.throwIfNotOwner(user, product.getOwnerId());
        productsRepository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
