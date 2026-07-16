package com.taskflow.auth.service;

import com.taskflow.auth.dto.LoginDTO;
import com.taskflow.auth.dto.RegistrationDTO;
import com.taskflow.auth.response.LoginResponse;

public interface AuthService {

    void register(RegistrationDTO request);

    LoginResponse login(LoginDTO request);
}