package com.taskflow.task.request;

import java.time.LocalDate;
import java.util.UUID;

import com.taskflow.task.enums.TaskPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotNull
    private UUID projectId;

    @NotBlank
    @Size(max = 200)
    private String title;

    private String description;

    @NotNull
    private TaskPriority priority;

    private UUID assignedTo;

    private LocalDate dueDate;

    private Integer estimatedHours;

}