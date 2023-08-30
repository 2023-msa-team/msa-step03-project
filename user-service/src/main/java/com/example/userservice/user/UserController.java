package com.example.userservice.user;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice._core.security.JwtTokenProvider;
import com.example.userservice._core.utils.ApiUtils;
import com.example.userservice.user.dto.UserRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {
        return ResponseEntity.ok(ApiUtils.success(userService.회원가입(joinDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
        String jwt = userService.로그인(loginDTO);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

}
