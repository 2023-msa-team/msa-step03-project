package com.example.userservice.user.dto;

import com.example.userservice.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
 
    @Getter @Setter
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
    @Getter @Setter
    public static class LoginDTO {
        private String userId;

        @JsonIgnore
        private String jwt;

        public LoginDTO(String userId, String jwt) {
            this.userId = userId;
            this.jwt = jwt;
        }
    }
}
