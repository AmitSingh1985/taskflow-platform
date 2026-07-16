package com.taskflow.auth.service;

import com.taskflow.auth.entity.User;

public interface JwtService {

	String generateToken(User user);

	String extractUsername(String token);

	boolean isTokenValid(String token);

}
