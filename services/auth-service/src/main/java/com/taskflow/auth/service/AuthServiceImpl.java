package com.taskflow.auth.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskflow.auth.dto.LoginDTO;
import com.taskflow.auth.dto.RegistrationDTO;
import com.taskflow.auth.entity.User;
import com.taskflow.auth.repository.UserRepository;
import com.taskflow.auth.response.LoginResponse;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Value("${jwt.expiration}")
    private long expiration;

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

		String token = jwtService.generateToken(user);

		LoginResponse response = new LoginResponse();
		response.setUserName(user.getUsername());
		response.setToken(token);
		response.setExpiresAt(new Date(System.currentTimeMillis() + expiration));

		return response;
	}
}