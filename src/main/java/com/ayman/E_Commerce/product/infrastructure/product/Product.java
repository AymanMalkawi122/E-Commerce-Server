package com.ayman.E_Commerce.product.infrastructure.product;

import com.ayman.E_Commerce.product.infrastructure.category.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long ownerId;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Valid
    private ProductCategory category;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false)
    private Double price;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double discountPercentage;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double rating;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long numberOfReviews;

    @NotNull
    @Column(nullable = false)
    private Integer stock;

    private String sku;

    @NotNull
    @Column(nullable = false)
    private String brand;

    @NotNull
    @DecimalMin("0.0")
    private Double weight;

    @Embedded
    private Dimensions dimensions;

    private String warrantyInformation;

    private String shippingInformation;

    @NotNull
    @Column(nullable = false)
    private String availabilityStatus;

    @ElementCollection
    private Set<String> tags;

    private String returnPolicy;

    @NotNull
    @Column(nullable = false)
    private Integer minimumOrderQuantity;

    private String barcode;

    private String qrCode;

    @ElementCollection
    private Set<String> images;

    @NotNull
    @Column(nullable = false)
    private String thumbnail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        rating = 0.0;
        numberOfReviews = 0L;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Dimensions {
    @NotNull
    @Column(nullable = false)
    private Double width;

    @NotNull
    @Column(nullable = false)
    private Double height;

    @NotNull
    @Column(nullable = false)
    private Double depth;
}

