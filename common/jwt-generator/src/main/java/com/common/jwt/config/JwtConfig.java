package com.common.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.common.jwt.service.JwtService;

@Configuration
@PropertySource("classpath:application-jwt.properties")
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Bean
    public JwtService jwtService() {
        return new JwtService(secret, expirationTime);
    }
}
