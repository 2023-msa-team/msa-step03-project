package com.example.userservice._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.example.userservice._core.filter.CorsFilter;
import com.example.userservice._core.filter.HasIpFilter;
import com.example.userservice._core.filter.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final Environment env;

    @Bean
    FilterRegistrationBean<HasIpFilter> hasIpFilter() {
        FilterRegistrationBean<HasIpFilter> bean = new FilterRegistrationBean<>(
                new HasIpFilter());
        bean.setOrder(0); // 낮은 번호 부터 실행
        return bean;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter());
        bean.setOrder(1); // 낮은 번호 부터 실행
        return bean;
    }

    @Bean
    FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter(env));
        bean.addUrlPatterns("/users/*");
        bean.addUrlPatterns("/orders/*");
        bean.setOrder(2); // 낮은 번호 부터 실행
        return bean;
    }
}
