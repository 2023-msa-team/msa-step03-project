package com.example.userservice._core.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.utils.FilterResponseUtils;
import com.example.userservice.user.User;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(JwtTokenProvider.HEADER);

        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);
            String pk = decodedJWT.getSubject();
            Long id = decodedJWT.getClaim("id").asLong();
            String email = decodedJWT.getClaim("email").asString();
            String roles = decodedJWT.getClaim("roles").asString();

            User user = User.builder().id(id).email(email).pk(pk).roles(roles).build();
            CustomUserDetails myUserDetails = new CustomUserDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            myUserDetails,
                            myUserDetails.getPassword(),
                            myUserDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureVerificationException sve) {
            FilterResponseUtils.unAuthorized(response, new Exception401("토큰 검증 실패"));
        } catch (TokenExpiredException tee) {
            FilterResponseUtils.unAuthorized(response, new Exception401("토큰 만료"));
        } finally {
            chain.doFilter(request, response);
        }
    }
}
