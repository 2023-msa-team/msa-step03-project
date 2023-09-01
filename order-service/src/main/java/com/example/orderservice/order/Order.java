package com.example.orderservice.order;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "order_tb")
@Entity
public class Order {
    @Id
    private String id;
    
    // catalog
    private String catalogId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    // user
    private String userId;

    @Builder
    public Order(String id, String catalogId, Integer qty, Integer unitPrice, Integer totalPrice,
            LocalDateTime createdAt, String userId) {
        this.id = id;
        this.catalogId = catalogId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    @PrePersist
    void onCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
