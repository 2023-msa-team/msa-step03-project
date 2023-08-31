package com.example.userservice._core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.utils.FilterResponseUtils;
import com.example.userservice._core.utils.JwtTokenUtils;
import com.example.userservice.user.User;


public class JwtAuthorizationFilter implements Filter {

    private Environment env;

    public JwtAuthorizationFilter(Environment env){
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("JWT 인가 필터 작동");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String jwt = request.getHeader("Authorization");

        // 토큰이 없거나, 공백이거나 스페이스 값만 있다면 401 응답
        if (jwt == null || jwt.isBlank()) {
            FilterResponseUtils.unAuthorized(response, new Exception401("토큰이 없습니다"));
            return;
        }

        try {
            // 1. 토큰 검증
            DecodedJWT decodedJWT = JwtTokenUtils.verify(jwt, env);

            // 2. 토큰을 request에 담아 두기 (Controller에서 사용하기 편하게 하기 위해)
            String id = decodedJWT.getSubject();
            String email = decodedJWT.getClaim("email").asString();
            User loginUser = User.builder().id(id).email(email).build();
            request.setAttribute("loginUser", loginUser);

            // 3. 다음 필터로 이동
            chain.doFilter(request, response);
        } catch (SignatureVerificationException | JWTDecodeException ve) {
            FilterResponseUtils.unAuthorized(response, new Exception401("토큰 검증 실패"));
        } catch (TokenExpiredException tee) {
            FilterResponseUtils.unAuthorized(response, new Exception401("토큰 만료"));
        }
    }
}
