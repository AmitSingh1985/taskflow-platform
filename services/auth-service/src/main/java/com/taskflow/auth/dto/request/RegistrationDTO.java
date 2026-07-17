package com.taskflow.auth.dto.request;

import lombok.Data;

@Data
public class RegistrationDTO {

	private String userName;
	private String email;
	private String password;

}
