package com.taskflow.ai.response;

import lombok.Data;

@Data
public class AiTaskResponse {

    private String title;

    private String description;

    private String priority;

    private String assignee;

    private String dueDate;
}