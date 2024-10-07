package com.ayman.E_Commerce.review.domain;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.core.UtilMethods;
import com.ayman.E_Commerce.product.domain.product.ProductsRepository;
import com.ayman.E_Commerce.product.infrastructure.product.Product;
import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewsService;
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
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ProductsRepository productsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public ResponseState<Review> createReview(Review review, User user) {
        final Optional<Product> optionalProduct = productsRepository.findById(review.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException(productsRepository.entityNotFoundMessage(review.getProductId().toString()));
        }
        final Product product = optionalProduct.get();

        return new ResponseState<>(
                reviewsRepository.ifIsNewElseThrow(review.getId(), () -> {
                            UtilMethods.throwIfNotOwner(user, review.getUserId());
                            product.setRating((product.getRating() * product.getNumberOfReviews() + review.getRating()) / (product.getNumberOfReviews() + 1));
                            product.setNumberOfReviews(product.getNumberOfReviews() + 1);
                            return reviewsRepository.save(review);
                        }
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseState<List<Review>> getReviews(Specification<Review> spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseState<>(reviewsRepository.findAll(spec, pageable).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseState<Optional<Review>> getReviewById(Long id, boolean throwIfNull) {
        final Optional<Review> result = reviewsRepository.findById(id);
        if (result.isEmpty() && throwIfNull) {
            throw new EntityNotFoundException(reviewsRepository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<Review> updateReview(Review review, User user) {
        final Optional<Product> optionalProduct = productsRepository.findById(review.getProductId());
        if(optionalProduct.isEmpty()) {
            throw new EntityNotFoundException(productsRepository.entityNotFoundMessage(review.getProductId().toString()));
        }
        final Product product = optionalProduct.get();

        Review result = reviewsRepository.ifExistsElseThrow(review.getId(), (oldReview) -> {
            product.setRating((product.getRating() * product.getNumberOfReviews() + review.getRating() - oldReview.getRating()) / product.getNumberOfReviews() );
            return reviewsRepository.save(review);
        });
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<String> deleteReviewById(Long id, User user) {
        final Optional<Review> optionalReview = reviewsRepository.findById(id);
        if(optionalReview.isEmpty()) {
            throw new EntityNotFoundException(reviewsRepository.entityNotFoundMessage(id.toString()));
        }
        final Review review = optionalReview.get();
        final Optional<Product> optionalProduct = productsRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new EntityNotFoundException(productsRepository.entityNotFoundMessage(review.getProductId().toString()));
        }
        final Product product = optionalProduct.get();

        product.setRating((product.getRating() * product.getNumberOfReviews() - review.getRating()) / (product.getNumberOfReviews() - 1) );
        product.setNumberOfReviews(product.getNumberOfReviews() - 1);

        reviewsRepository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
