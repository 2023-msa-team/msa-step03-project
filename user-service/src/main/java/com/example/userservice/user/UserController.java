package com.example.userservice.user;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UserController {

    private final UserService userService;
    private final Environment env;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
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
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, respDTO.getJwt()).body(ApiUtils.success(respDTO));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        UserResponse.DetailDTO respDTO = userService.유저상세(id);
        return ResponseEntity.ok(ApiUtils.success(respDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll(){
        List<UserResponse.ListDTO> respDTOs = userService.유저목록();
        return ResponseEntity.ok(ApiUtils.success(respDTOs));
    }

}
