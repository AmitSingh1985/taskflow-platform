package com.taskflow.project.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.taskflow.project.enums.ProjectRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMemberResponse {

    private UUID id;

    private UUID projectId;

    private UUID userId;

    private ProjectRole role;

    private LocalDateTime joinedAt;

}