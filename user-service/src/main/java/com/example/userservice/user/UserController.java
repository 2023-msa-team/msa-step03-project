package com.example.userservice.user;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice._core.jwt.JwtTokenProvider;
import com.example.userservice._core.utils.ApiUtils;
import com.example.userservice.user.dto.UserRequest;
import com.example.userservice.user.dto.UserResponse;

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
    public ResponseEntity<?> join(@Validated @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        return ResponseEntity.ok(ApiUtils.success(userService.회원가입(reqDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        UserResponse.LoginDTO respDTO = userService.로그인(reqDTO);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, respDTO.getJwt()).body(ApiUtils.success(respDTO));
    }

}
