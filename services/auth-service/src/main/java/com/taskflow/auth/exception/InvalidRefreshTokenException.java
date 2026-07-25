package com.taskflow.auth.exception;

import com.taskflow.common.exception.BusinessException;

public class InvalidRefreshTokenException extends BusinessException {

    public InvalidRefreshTokenException() {
        super("Invalid refresh token");
    }

}