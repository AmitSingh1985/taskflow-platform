package com.taskflow.task.response;

import java.time.LocalDate;
import java.util.UUID;

import com.taskflow.task.enums.TaskPriority;
import com.taskflow.task.enums.TaskStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

    private UUID id;

    private UUID projectId;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private UUID assignedTo;

    private UUID createdBy;

    private LocalDate dueDate;

    private Integer estimatedHours;

}