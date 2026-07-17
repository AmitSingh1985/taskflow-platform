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
import com.taskflow.auth.dto.request.LoginDTO;
import com.taskflow.auth.dto.request.RegistrationDTO;
import com.taskflow.auth.dto.rseponse.LoginResponse;
import com.taskflow.auth.entity.User;
import com.taskflow.auth.repository.UserRepository;
import com.taskflow.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Value("${jwt.expiration}")
    private long expiration;
	
	@Value("${jwt.secret}")
    private String secret;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public void register(RegistrationDTO request) {

		if (userRepository.existsByUsername(request.getUserName())) {
			throw new RuntimeException("Username already exists.");
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists.");
		}

		User user = new User();

		user.setId(UUID.randomUUID());
		user.setUsername(request.getUserName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(true);
		user.setCreatedAt(LocalDateTime.now());

		userRepository.save(user);
	}

	@Override
	public LoginResponse login(LoginDTO request) {

		User user = userRepository.findByUsername(request.getUserName())
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}

		// String token = jwtService.generateToken(user);
		Map<String,Object> map =new HashMap<String, Object>();

		String token = jwtService.generateToken(user.getUsername(),map);

		LoginResponse response = new LoginResponse();
		response.setUserName(user.getUsername());
		response.setToken(token);
		response.setExpiresAt(new Date(System.currentTimeMillis() + expiration));

		return response;
	}
}