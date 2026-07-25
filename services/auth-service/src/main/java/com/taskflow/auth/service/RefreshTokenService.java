package com.taskflow.auth.service;

import java.util.UUID;

import com.taskflow.auth.entity.RefreshToken;
import com.taskflow.auth.entity.User;

public interface RefreshTokenService {

	RefreshToken createRefreshToken(User user);

	RefreshToken verifyRefreshToken(String token);

	RefreshToken rotateRefreshToken(RefreshToken refreshToken);

	void revokeAllUserTokens(UUID userId);

}