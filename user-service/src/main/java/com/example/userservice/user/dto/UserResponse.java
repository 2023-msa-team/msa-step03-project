package com.example.userservice.user.dto;

import com.example.userservice.user.User;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
 
    @Getter @Setter
    public static class JoinDTO {
        private String email;
        private String username;
        private String pk;

        public JoinDTO(User user) {
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.pk = user.getPk();
        }
    }
}
