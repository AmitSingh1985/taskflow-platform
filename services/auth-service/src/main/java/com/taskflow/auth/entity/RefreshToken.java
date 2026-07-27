package com.taskflow.auth.entity;

import java.time.LocalDateTime;

import com.taskflow.jpa.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "refresh_token")

@Setter
@Getter
public class RefreshToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "expiry_date", nullable = false)
	private LocalDateTime expiryDate;

	@Column(nullable = false)
	private boolean revoked;

	/*
	 * @Column(name = "created_at") private LocalDateTime createdAt;
	 * 
	 * @Column(name = "updated_at") private LocalDateTime updatedAt;
	 */
}