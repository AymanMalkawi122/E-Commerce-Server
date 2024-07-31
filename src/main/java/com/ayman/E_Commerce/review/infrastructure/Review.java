package com.ayman.E_Commerce.review.infrastructure;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull
    @Column(nullable = false)
    private String reviewerName;

    @NotNull
    @Email
    @Column(nullable = false)
    private String reviewerEmail;

    @NotNull
    @Column(nullable = false)
    private Long reviewerId;

    @NotNull
    @Column(nullable = false)
    private Long productId;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }
}

