package com.taskflow.task.OpenFeign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.taskflow.task.dto.internal.ProjectAccessResponse;
import com.taskflow.task.dto.internal.ProjectInfoResponse;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectClient {

    @GetMapping("/api/internal/projects/{projectId}")
    ProjectInfoResponse getProject(
            @PathVariable UUID projectId);

    @GetMapping("/api/internal/projects/{projectId}/access/{userId}")
    ProjectAccessResponse hasAccess(
            @PathVariable UUID projectId,
            @PathVariable UUID userId);

}