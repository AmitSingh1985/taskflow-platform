package com.taskflow.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.application.security.jwt")
public class JwtSecurityProperties {

	private long accessTokenValidityMinutes;

	private long refreshTokenValidityDays;

}