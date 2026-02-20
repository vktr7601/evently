package com.evently.notification.securityConfig;


import jwt.JWTUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public JWTUtility jwtUtility() {
        return new JWTUtility();
    }
}