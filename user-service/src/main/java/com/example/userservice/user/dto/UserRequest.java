package com.example.userservice.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.userservice.user.User;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {
    
    @Getter @Setter
    public static class JoinDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        private String username;

        @NotEmpty
        @Size(min = 3, max = 20, message = "3에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        public User toEntity(String uuid, String encPassword){
            return User.builder()
                .id(uuid)
                .email(email)
                .username(username)
                .password(encPassword)
                .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 3, max = 20, message = "3에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;
    }
}
