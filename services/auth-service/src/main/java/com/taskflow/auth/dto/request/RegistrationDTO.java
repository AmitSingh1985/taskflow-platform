package com.taskflow.auth.dto.request;

import lombok.Data;

@Data
public class RegistrationDTO {

	private String username;
	private String email;
	private String password;

}
