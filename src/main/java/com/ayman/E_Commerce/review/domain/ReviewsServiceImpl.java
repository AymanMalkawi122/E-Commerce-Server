package com.ayman.E_Commerce.review.domain;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.product.infrastructure.product.ProductsService;
import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewsService;
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
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final ProductsService productsService;

    @Autowired
    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ProductsService productsService) {
        this.reviewsRepository = reviewsRepository;
        this.productsService = productsService;
    }

    @Override
    public ResponseState<Review> createReview(Review review) {
        final boolean productExists = productsService.existsById(review.getProductId()).getData();
        if(!productExists) {
            throw new EntityNotFoundException(BaseRepository.entityNotFoundMessage("Product", review.getProductId().toString()));
        }
        //TODO check if user is valid
        return new ResponseState<>(reviewsRepository.save(review), HttpStatus.CREATED);
    }

    @Override
    public ResponseState<List<Review>> getReviews(Specification<Review> spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(reviewsRepository.findAll(spec, pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Optional<Review>> getReviewById(Long id) {
        final Optional<Review> result = reviewsRepository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException(BaseRepository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Review> updateReview(Review review) {
        Review result = reviewsRepository.ifExistsElseThrow(review.getId(), () -> reviewsRepository.save(review));
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteReviewById(Long id) {
        reviewsRepository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
