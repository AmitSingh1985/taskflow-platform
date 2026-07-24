package com.taskflow.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	private UUID id;

	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String password;

	private Boolean enabled;

	private LocalDateTime createdAt;

}