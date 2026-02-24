package com.evently.gateway.config;

import jwt.JWTUtility;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public JWTUtility jwtUtility() {
        return new JWTUtility();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter(JWTUtility jwtUtility) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(jwtUtility));
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
