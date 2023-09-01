package com.example.userservice.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.errors.exception.Exception404;
import com.example.userservice._core.errors.exception.Exception500;
import com.example.userservice._core.utils.JwtTokenUtils;
import com.example.userservice.user.dto.UserRequest;
import com.example.userservice.user.dto.UserResponse;
import com.example.userservice.user.dto.UserResponse.ListDTO;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Environment env;

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO reqDTO) {
        try {
            // 동일한 서버가 2개 생기고, 디비가 2개 생기면, id를 auto_increment로 하면 사용자를 구분할 수 없음
            String uuid = UUID.randomUUID().toString();
            String encPassword = BCrypt.hashpw(reqDTO.getPassword(), BCrypt.gensalt());
            User userPS = userRepository.save(reqDTO.toEntity(uuid, encPassword));
            UserResponse.JoinDTO respDTO = new UserResponse.JoinDTO(userPS);
            return respDTO;
        } catch (Exception e) {
            throw new Exception500(e.getMessage());
        }
    }

    public UserResponse.LoginDTO 로그인(UserRequest.LoginDTO reqDTO) {
        User userPS = userRepository.findByEmail(reqDTO.getEmail())
                .orElseThrow(() -> new Exception404("이메일을 찾을 수 없습니다 : "+reqDTO.getEmail()));
        boolean isValid = BCrypt.checkpw(reqDTO.getPassword(), userPS.getPassword());

        if (!isValid) {
            throw new Exception401("아이디 혹은 비밀번호가 틀렸습니다");
        }
        try {
            String jwt = JwtTokenUtils.create(userPS, env);
            return new UserResponse.LoginDTO(userPS.getId(), jwt);
        } catch (Exception e) {
            throw new Exception401("인증 토큰 생성에 실패하였습니다 : "+e.getMessage());
        }
    }

    public UserResponse.DetailDTO 유저상세(String id) {
        User userPS = userRepository.findById(id)
            .orElseThrow(() -> new Exception404("유저 id를 찾을 수 없습니다 : "+id));
        return new UserResponse.DetailDTO(userPS);
    }

    public List<ListDTO> 유저목록() {
        List<User> userListPS = userRepository.findAll();
        return userListPS.stream().map(UserResponse.ListDTO::new).collect(Collectors.toList());
    }
}
