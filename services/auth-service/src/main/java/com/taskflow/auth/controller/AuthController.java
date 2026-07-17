package com.taskflow.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.auth.dto.request.LoginDTO;
import com.taskflow.auth.dto.request.RegistrationDTO;
import com.taskflow.auth.dto.rseponse.LoginResponse;
import com.taskflow.auth.service.AuthService;
import com.taskflow.common.api.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController<T> {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<RegistrationDTO>> register(@RequestBody RegistrationDTO request) {

		authService.register(request);

		return ResponseEntity.ok(ApiResponse.success("User registered successfully", request));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginDTO request) {

		LoginResponse loginResponse = authService.login(request);

		return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));

	}
}