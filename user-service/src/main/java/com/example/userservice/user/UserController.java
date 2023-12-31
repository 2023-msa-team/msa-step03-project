package com.example.userservice.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.errors.exception.Exception403;
import com.example.userservice._core.utils.ApiUtils;
import com.example.userservice._core.utils.JwtTokenUtils;
import com.example.userservice.user.dto.UserRequest;
import com.example.userservice.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final Environment env;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck(HttpServletRequest request) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("jwt 토큰 검증 : " + jwt);
        if (jwt != null) {
            DecodedJWT decodedJWT = JwtTokenUtils.verify(jwt, env);
            String subject = decodedJWT.getSubject();
            System.out.println("userId : " + subject);
        }

        String responseBody = String.format("user-service on Port %s", env.getProperty("local.server.port"));
        return ResponseEntity.ok(ApiUtils.success(responseBody));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.JoinDTO respDTO = userService.회원가입(reqDTO);
        return ResponseEntity.ok(ApiUtils.success(respDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        UserResponse.LoginDTO respDTO = userService.로그인(reqDTO);
        return ResponseEntity.ok().header("Authorization", respDTO.getJwt()).body(ApiUtils.success(respDTO));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, @RequestHeader("X-Authorization-Id") String loginUserId) {
        if(!id.equals(loginUserId)){
            throw new Exception403("해당 유저는 권한이 없습니다 : "+loginUserId);
        }

        UserResponse.DetailDTO respDTO = userService.유저상세(id);
        return ResponseEntity.ok(ApiUtils.success(respDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll() {
        List<UserResponse.ListDTO> respDTOs = userService.유저목록();
        return ResponseEntity.ok(ApiUtils.success(respDTOs));
    }

}
