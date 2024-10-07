package com.ayman.E_Commerce.cart.infrastructure;

import com.ayman.E_Commerce.product.infrastructure.product.Product;
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
import java.util.Set;

@Entity(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    Set<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(long productId) {
        products.removeIf((product)-> product.getId() == productId);
    }
}

