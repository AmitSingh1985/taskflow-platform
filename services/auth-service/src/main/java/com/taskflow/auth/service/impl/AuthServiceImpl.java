package com.taskflow.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.common.jwt.service.JwtService;
import com.taskflow.auth.config.JwtSecurityProperties;
import com.taskflow.auth.dto.request.LoginDTO;
import com.taskflow.auth.dto.request.RefreshTokenRequest;
import com.taskflow.auth.dto.request.RegistrationDTO;
import com.taskflow.auth.dto.rseponse.LoginResponse;
import com.taskflow.auth.dto.rseponse.RefreshTokenResponse;
import com.taskflow.auth.entity.RefreshToken;
import com.taskflow.auth.entity.User;
import com.taskflow.auth.exception.InvalidCredentialsException;
import com.taskflow.auth.exception.UserAlreadyExistsException;
import com.taskflow.auth.repository.UserRepository;
import com.taskflow.auth.service.AuthService;
import com.taskflow.auth.service.RefreshTokenService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final JwtSecurityProperties jwtProperties;


	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			RefreshTokenService refreshTokenService,JwtSecurityProperties jwtSecurityProperties) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.jwtProperties = jwtSecurityProperties;
	}

	@Override
	public void register(RegistrationDTO request) {

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UserAlreadyExistsException(request.getUsername());
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistsException(request.getEmail());
		}

		User user = new User();

		user.setId(UUID.randomUUID());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(true);
		user.setCreatedAt(LocalDateTime.now());

		userRepository.save(user);
	}

	@Override
	public LoginResponse login(LoginDTO request) {

		User user = userRepository.findByUsername(request.getUserName())
				.orElseThrow(() -> new InvalidCredentialsException());

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException();
		}
		
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("userId", user.getId());
		///map.put("role", user.getRole().name());
		Long expirationInMillis=jwtProperties.getAccessTokenValidityMinutes() * 60 * 1000L;

		String accessToken = jwtService.generateToken(user.getUsername(),map,expirationInMillis);

		RefreshToken refreshToken =
		        refreshTokenService.createRefreshToken(user);
		
		Date expiresIn= jwtService.extractExpiration(accessToken);

		return LoginResponse.builder().username(request.getUserName())
		        .accessToken(accessToken)
		        .refreshToken(refreshToken.getToken())
		        .tokenType("Bearer")
		        .expiresIn(expiresIn)
		        .build();
	}
	
	@Override
	@Transactional
	public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

	    log.info("Refreshing access token");

	    RefreshToken existingToken =
	            refreshTokenService.verifyRefreshToken(request.refreshToken());

	    User user = existingToken.getUser();

	    RefreshToken newRefreshToken =
	            refreshTokenService.rotateRefreshToken(existingToken);
	    Map<String,Object> map=new HashMap<String, Object>();
	    map.put("userId", user.getId());
	    Long expirationInMillis=jwtProperties.getAccessTokenValidityMinutes() * 60 * 1000L;
	    String accessToken = jwtService.generateToken(user.getUsername(),map,expirationInMillis);
        
	    log.info("Access token refreshed successfully for user {}", user.getEmail());

	    return RefreshTokenResponse.builder()
	            .accessToken(accessToken)
	            .refreshToken(newRefreshToken.getToken())
	            .tokenType("Bearer")
	            .expiresIn(jwtProperties.getAccessTokenValidityMinutes() * 60)
	            .build();
	}
}