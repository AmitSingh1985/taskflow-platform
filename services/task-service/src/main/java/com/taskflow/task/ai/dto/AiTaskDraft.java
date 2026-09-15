package com.taskflow.task.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiTaskDraft {

    private String title;

    private String description;

    private String priority;

    private String assignee;

    private String dueDate;

    private Double complexity;

    private Double urgency;

    private Integer dependencyCount;

    private Double estimatedHours;
}