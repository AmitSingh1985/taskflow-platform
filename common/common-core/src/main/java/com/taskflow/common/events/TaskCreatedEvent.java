package com.taskflow.common.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreatedEvent {

	private String taskId;

	private String projectId;

	private String assignedUserId;

	private String title;

	private String status;

	private LocalDateTime createdAt;

	// Below Added for AI Prediction for Task Priority

	private String priority;

	private double complexity;

	private double urgency;

	private double dependencyCount;

	private double estimatedHours;

	private String description;
}