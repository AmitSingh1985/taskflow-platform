package com.taskflow.project.request;

import java.util.UUID;

import com.taskflow.project.enums.ProjectRole;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProjectMemberRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private ProjectRole role;

}