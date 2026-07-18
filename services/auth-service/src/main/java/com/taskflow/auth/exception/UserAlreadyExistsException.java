package com.taskflow.auth.exception;

import com.taskflow.common.exception.BusinessException;

public class UserAlreadyExistsException extends BusinessException {

	public UserAlreadyExistsException(String id) {

		super("User already exists with email or User : " + id);

	}

}