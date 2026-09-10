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

}