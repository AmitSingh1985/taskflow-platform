package com.taskflow.auth.dto.request;

import lombok.Data;

@Data
public class LoginDTO {

	private String userName;
	private String password;
}
