package com.taskflow.project.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.project.dto.internal.ProjectAccessResponse;
import com.taskflow.project.dto.internal.ProjectInfoResponse;
import com.taskflow.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/internal/projects")
@RequiredArgsConstructor
public class InternalProjectController {

    private final ProjectService projectService;
    
    @GetMapping("/{projectId}")
    public ProjectInfoResponse getProject(
            @PathVariable UUID projectId) {

        return projectService.getProjectInfo(projectId);

    }
    
    @GetMapping("/{projectId}/access/{userId}")
    public ProjectAccessResponse hasAccess(
            @PathVariable UUID projectId,
            @PathVariable UUID userId) {

        return projectService.hasAccess(projectId, userId);

    }

}