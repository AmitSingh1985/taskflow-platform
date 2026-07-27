package com.taskflow.auth.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.auth.entity.RefreshToken;
import com.taskflow.auth.entity.User;
import com.taskflow.auth.exception.InvalidRefreshTokenException;
import com.taskflow.auth.exception.RefreshTokenExpiredException;
import com.taskflow.auth.repository.RefreshTokenRepository;
import com.taskflow.auth.service.RefreshTokenService;
import com.taskflow.auth.util.RefreshTokenGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7;

	@Override
	public RefreshToken createRefreshToken(User user) {
		log.info("Generating refresh token for user {}", user.getUsername());

		RefreshToken refreshToken = new RefreshToken();//RefreshToken.builder().token(RefreshTokenGenerator.generate()).user(user)
				//.expiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS)).revoked(false).build();
		refreshToken.setToken(RefreshTokenGenerator.generate());
		refreshToken.setUser(user);
		refreshToken.setExpiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS));
		refreshToken.setRevoked(false);
		refreshToken.setCreatedAt(LocalDateTime.now());
		refreshToken.setUpdatedAt(LocalDateTime.now());
		RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

		log.info("Refresh token generated successfully for user {}", user.getUsername());

		return savedToken;
	}

	@Override
	public RefreshToken verifyRefreshToken(String token) {
		log.info("Verifying refresh token");

		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> {
			log.error("Refresh token not found");
			return new InvalidRefreshTokenException();
		});

		if (Boolean.TRUE.equals(refreshToken.isRevoked())) {

			log.error("Refresh token has been revoked");

			throw new InvalidRefreshTokenException();
		}

		if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

			log.error("Refresh token has expired");
			refreshTokenRepository.delete(refreshToken);

			throw new RefreshTokenExpiredException();
		}

		log.info("Refresh token verified successfully");

		return refreshToken;
	}

	@Override
	@Transactional
	public RefreshToken rotateRefreshToken(RefreshToken existingToken) {

		log.info("Rotating refresh token for user {}", existingToken.getUser().getEmail());

		existingToken.setRevoked(true);

		refreshTokenRepository.save(existingToken);

		return createRefreshToken(existingToken.getUser());
	}

	@Override
	public void revokeAllUserTokens(UUID userId) {
		// TODO
	}

}