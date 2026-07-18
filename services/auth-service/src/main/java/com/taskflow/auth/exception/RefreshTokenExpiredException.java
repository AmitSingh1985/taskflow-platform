package com.taskflow.auth.exception;

import com.taskflow.common.exception.BusinessException;

public class RefreshTokenExpiredException extends BusinessException {

    public RefreshTokenExpiredException() {
        super("Refresh token has expired.");
    }
}