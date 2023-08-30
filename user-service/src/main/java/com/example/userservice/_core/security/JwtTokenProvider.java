package com.example.userservice._core.security;


import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.user.User;


@Component
public class JwtTokenProvider {
    public static final Long EXP = 1000L * 60 * 60 * 48; // 48시간 - 테스트 하기 편함.
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";
    public static final String SECRET = "metacoding";

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(user.getPk())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("roles", user.getRoles())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        jwt = jwt.replace(JwtTokenProvider.TOKEN_PREFIX, "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }

}
