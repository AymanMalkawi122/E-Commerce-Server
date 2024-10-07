package com.ayman.E_Commerce.product.domain.category;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import com.ayman.E_Commerce.product.infrastructure.category.ProductCategoriesService;
import com.ayman.E_Commerce.product.infrastructure.category.ProductCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {
    final ProductCategoriesRepository repository;

    @Autowired
    public ProductCategoriesServiceImpl(ProductCategoriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseState<ProductCategory> createProductCategory(ProductCategory category) {
        repository.throwIfExistsByName(category.getName(), new RepositoryException("category name must be unique", HttpStatus.BAD_REQUEST));
        return new ResponseState<>(repository.save(category), HttpStatus.OK);
    }

    @Override
    public ResponseState<List<ProductCategory>> getProductCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(repository.findAll(pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Optional<ProductCategory>> getProductCategoryById(Long id) {
        final Optional<ProductCategory> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException(repository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<ProductCategory> updateProductCategory(ProductCategory category) {
        ProductCategory result = repository.ifExistsElseThrow(category.getId(), () -> repository.save(category));
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteProductCategoryById(Long id) {
        repository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
