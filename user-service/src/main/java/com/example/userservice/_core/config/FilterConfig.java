package com.example.userservice._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.userservice._core.jwt.JwtAuthorizationFilter;

@Configuration
public class FilterConfig {

    @Bean
    FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter());
        bean.addUrlPatterns("/users/*");
        bean.addUrlPatterns("/orders/*");
        bean.setOrder(0); // 낮은 번호 부터 실행
        return bean;
    }
}
