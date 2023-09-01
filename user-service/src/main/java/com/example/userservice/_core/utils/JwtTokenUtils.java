package com.example.userservice._core.utils;

import java.time.Instant;

import org.springframework.core.env.Environment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice.user.User;

public class JwtTokenUtils {

    public static String create(User user, Environment env) {
        String secret = env.getProperty("token.secret");
        Long exp = Long.parseLong(env.getProperty("token.exp"));

        String jwt = JWT.create()
                .withSubject(user.getId()) // UUID
                .withExpiresAt(Instant.now().plusMillis(exp))
                .withClaim("email", user.getEmail())
                .sign(Algorithm.HMAC512(secret));
        return "Bearer " + jwt;
    }

    public static DecodedJWT verify(String jwt, Environment env)
            throws SignatureVerificationException, TokenExpiredException {
        String secret = env.getProperty("token.secret");

        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                .build().verify(jwt);
        return decodedJWT;
    }

}
