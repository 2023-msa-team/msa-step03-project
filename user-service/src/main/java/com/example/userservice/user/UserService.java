package com.example.userservice.user;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.errors.exception.Exception500;
import com.example.userservice._core.security.CustomUserDetails;
import com.example.userservice._core.security.JwtTokenProvider;
import com.example.userservice.user.dto.UserRequest;
import com.example.userservice.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO joinDTO) {
        try {
            // 동일한 서버가 2개 생기고, 디비가 2개 생기면, auto_increment로 사용자 구분할 수 없음
            String pk = UUID.randomUUID().toString();
            String encPassword = passwordEncoder.encode(joinDTO.getPassword());
            return new UserResponse.JoinDTO(userRepository.save(joinDTO.toEntity(pk, encPassword)));
        } catch (Exception e) {
            throw new Exception500(e.getMessage());
        }
    }

    public String 로그인(UserRequest.LoginDTO requestDTO) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(), requestDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return JwtTokenProvider.create(myUserDetails.getUser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception401("인증되지 않았습니다");
        }
    }
}
