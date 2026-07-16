package com.taskflow.auth.response;

import java.util.Date;

import lombok.Data;

@Data
public class LoginResponse {
	private String userName;
	private String token;
	private Date expiresAt;
}
