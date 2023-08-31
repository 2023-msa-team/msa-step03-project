package com.example.catalogservice.catalog.dto;

import com.example.catalogservice._core.utils.DateUtils;
import com.example.catalogservice.catalog.Catalog;

import lombok.Getter;
import lombok.Setter;

public class CatalogResponse {
    
    @Getter @Setter
    public static class ListDTO {
        private String catalogId;
        private String name;
        private Integer stock;
        private Integer unitPrice;
        private String createdAt;

        public ListDTO(Catalog catalog) {
            this.catalogId = catalog.getId();
            this.name = catalog.getName();
            this.stock = catalog.getStock();
            this.unitPrice = catalog.getUnitPrice();
            this.createdAt = DateUtils.toString(catalog.getCreatedAt());
        }    
    }
}
