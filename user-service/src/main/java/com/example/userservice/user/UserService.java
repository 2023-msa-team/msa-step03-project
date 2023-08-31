package com.example.userservice.user;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.errors.exception.Exception404;
import com.example.userservice._core.errors.exception.Exception500;
import com.example.userservice._core.jwt.JwtTokenProvider;
import com.example.userservice.user.dto.UserRequest;
import com.example.userservice.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO reqDTO) {
        try {
            // 동일한 서버가 2개 생기고, 디비가 2개 생기면, id를 auto_increment로 하면 사용자를 구분할 수 없음
            String id = UUID.randomUUID().toString();
            String encPassword = BCrypt.hashpw(reqDTO.getPassword(), BCrypt.gensalt());
            User userPS = userRepository.save(reqDTO.toEntity(id, encPassword));
            UserResponse.JoinDTO respDTO = new UserResponse.JoinDTO(userPS);
            return respDTO;
        } catch (Exception e) {
            throw new Exception500(e.getMessage());
        }
    }

    public String 로그인(UserRequest.LoginDTO reqDTO) {

        User userPS = userRepository.findByEmail(reqDTO.getEmail())
                .orElseThrow(() -> new Exception404("이메일을 찾을 수 없습니다 : "+reqDTO.getEmail()));
        boolean isValid = BCrypt.checkpw(reqDTO.getPassword(), userPS.getPassword());

        if (!isValid) {
            throw new Exception401("아이디 혹은 비밀번호가 틀렸습니다");
        }
        try {
            return JwtTokenProvider.create(userPS);
        } catch (Exception e) {
            throw new Exception401("인증 토큰 생성에 실패하였습니다 : "+e.getMessage());
        }
    }
}
