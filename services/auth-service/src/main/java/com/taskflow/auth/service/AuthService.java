package com.taskflow.auth.service;

import com.taskflow.auth.dto.request.LoginDTO;
import com.taskflow.auth.dto.request.RefreshTokenRequest;
import com.taskflow.auth.dto.request.RegistrationDTO;
import com.taskflow.auth.dto.rseponse.LoginResponse;
import com.taskflow.auth.dto.rseponse.RefreshTokenResponse;

public interface AuthService {

    void register(RegistrationDTO request);

    LoginResponse login(LoginDTO request);
    
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}