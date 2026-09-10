package com.taskflow.common.events;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreatedEvent {

    private String projectId;

    private String name;

    private String description;

    private String status;

    private String ownerId;

    private LocalDate startDate;

    private LocalDate endDate;
}