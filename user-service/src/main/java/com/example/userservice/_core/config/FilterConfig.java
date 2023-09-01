package com.example.userservice._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.userservice._core.filter.HasIpFilter;


@Configuration
public class FilterConfig {

    @Bean
    FilterRegistrationBean<HasIpFilter> hasIpFilter() {
        FilterRegistrationBean<HasIpFilter> bean = new FilterRegistrationBean<>(
                new HasIpFilter());
        bean.setOrder(0); // 낮은 번호 부터 실행
        return bean;
    }
}
