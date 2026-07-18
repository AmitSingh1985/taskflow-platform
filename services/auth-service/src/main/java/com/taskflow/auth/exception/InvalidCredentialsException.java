package com.taskflow.auth.exception;

import com.taskflow.common.exception.BusinessException;

public class InvalidCredentialsException extends BusinessException {

	public InvalidCredentialsException() {

		super("Invalid username or password");

	}

}