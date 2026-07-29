package com.taskflow.project.response;

import java.time.LocalDate;
import java.util.UUID;

import com.taskflow.project.enums.ProjectStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

    private UUID id;

    private String name;

    private String description;

    private ProjectStatus status;

    private UUID ownerId;

    private LocalDate startDate;

    private LocalDate endDate;

}