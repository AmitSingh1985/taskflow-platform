package com.taskflow.task.request;

import java.time.LocalDate;
import java.util.UUID;

import com.taskflow.task.enums.TaskPriority;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    private TaskPriority priority;

    private UUID assignedTo;

    private LocalDate dueDate;

    private Integer estimatedHours;

}