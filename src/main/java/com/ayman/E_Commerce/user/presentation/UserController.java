package com.ayman.E_Commerce.user.presentation;

import com.ayman.E_Commerce.review.domain.ReviewSpecification;
import com.ayman.E_Commerce.review.infrastructure.Review;
import com.ayman.E_Commerce.review.infrastructure.ReviewsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
@Validated
public class UserController {

}
