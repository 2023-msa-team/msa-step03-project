package com.example.gatewayservice.utils;

import org.springframework.core.env.Environment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenUtils {

    public static DecodedJWT verify(String jwt, Environment env)
            throws SignatureVerificationException, TokenExpiredException, JWTDecodeException {
        String secret = env.getProperty("token.secret");

        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                .build().verify(jwt);
        return decodedJWT;
    }

}
