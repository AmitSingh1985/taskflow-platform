package com.taskflow.auth.dto.rseponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
	private String username;
	private String accessToken;

	private String refreshToken;

	private String tokenType;

	private Long expiresIn;
}
