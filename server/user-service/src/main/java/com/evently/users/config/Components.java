package com.evently.users.config;

import jwt.JWTUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Components {
    @Bean
    public JWTUtility jwtUtility() {
        return new JWTUtility();
    }
}