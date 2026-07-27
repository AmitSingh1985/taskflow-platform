package com.taskflow.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class SecurityConfiguration {
}