package com.example.orderservice.order.dto;

import com.example.orderservice._core.utils.DateUtils;
import com.example.orderservice.order.Order;

import lombok.Getter;
import lombok.Setter;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO {
        private String catalogId;
        private Integer qty;
        private Integer unitPrice;
        private Integer totalPrice;
        private String createdAt;

        public SaveDTO(Order order) {
            this.catalogId = order.getCatalogId();
            this.qty = order.getQty();
            this.unitPrice = order.getUnitPrice();
            this.totalPrice = order.getTotalPrice();
            this.createdAt = DateUtils.toString(order.getCreatedAt());
        }
    }

    @Getter
    @Setter
    public static class ListDTO {
        private String catalogId;
        private Integer qty;
        private Integer unitPrice;
        private Integer totalPrice;
        private String createdAt;

        public ListDTO(Order order) {
            this.catalogId = order.getCatalogId();
            this.qty = order.getQty();
            this.unitPrice = order.getUnitPrice();
            this.totalPrice = order.getTotalPrice();
            this.createdAt = DateUtils.toString(order.getCreatedAt());
        }
    }
}
