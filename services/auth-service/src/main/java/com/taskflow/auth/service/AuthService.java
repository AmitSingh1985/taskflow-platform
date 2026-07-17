package com.taskflow.auth.service;

import com.taskflow.auth.dto.request.LoginDTO;
import com.taskflow.auth.dto.request.RegistrationDTO;
import com.taskflow.auth.dto.rseponse.LoginResponse;

public interface AuthService {

    void register(RegistrationDTO request);

    LoginResponse login(LoginDTO request);
}