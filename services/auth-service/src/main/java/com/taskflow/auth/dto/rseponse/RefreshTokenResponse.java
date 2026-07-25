package com.taskflow.auth.dto.rseponse;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(

		String accessToken,

		String refreshToken,

		String tokenType,

		Long expiresIn

) {
}