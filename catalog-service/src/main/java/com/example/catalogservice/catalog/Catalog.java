package com.example.catalogservice.catalog;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "catalog_tb")
@Entity
public class Catalog {
    @Id
    private String id;
    private String name;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    @Builder
    public Catalog(String id, String name, Integer stock, Integer unitPrice, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
    }

    @PrePersist
    void onCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
