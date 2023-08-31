package com.example.userservice.user.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.userservice.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    public static class JoinDTO {
        private String userId;
        private String email;
        private String username;

        public JoinDTO(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.username = user.getUsername();
        }
    }

    // 토큰은 header로 응답할 것이고, user의 id는 DTO로 service에서 받기 위해 DTO 만듬.
    @Getter
    @Setter
    public static class LoginDTO {
        private String userId;

        @JsonIgnore
        private String jwt;

        public LoginDTO(String userId, String jwt) {
            this.userId = userId;
            this.jwt = jwt;
        }
    }

    @Getter
    @Setter
    public static class DetailDTO {
        private String userId;
        private String email;
        private String username;
        private List<OrderDTO> orders;

        public DetailDTO(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.orders = new ArrayList<>();
        }

        @Getter
        @Setter
        public class OrderDTO {
            private String orderId;
            private String productId;
            private Integer qty;
            private Integer unitPrice;
            private Integer totalPrice;
            private String createdAt;
        }
    }

    @Getter
    @Setter
    public static class ListDTO {
        private String userId;
        private String email;
        private String username;

        public ListDTO(User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.username = user.getUsername();
        }
    }
}
