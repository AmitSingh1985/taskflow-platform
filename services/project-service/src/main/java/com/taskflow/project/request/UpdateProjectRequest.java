package com.taskflow.project.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProjectRequest {

    @NotBlank
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}