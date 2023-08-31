package com.example.catalogservice.catalog;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "catalog_tb")
@Entity
public class Catalog {
    @Id
    private String id;

    @Column(unique = true)
    private String name;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    @PrePersist
    void onCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
