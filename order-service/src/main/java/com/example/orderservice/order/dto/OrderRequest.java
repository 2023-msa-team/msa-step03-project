package com.example.orderservice.order.dto;

import com.example.orderservice.order.Order;

import lombok.Getter;
import lombok.Setter;

public class OrderRequest {
    
    @Getter @Setter
    public static class SaveDTO {
        private String catalogId;
        private Integer qty;
        private Integer unitPrice;
        private String userId;

        public Order toEntity(String uuid){
            return Order.builder()
                .id(uuid)
                .catalogId(catalogId)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(qty * unitPrice)
                .userId(userId)
                .build();
        }
    }
}
