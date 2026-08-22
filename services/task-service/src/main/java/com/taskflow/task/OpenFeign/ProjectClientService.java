package com.taskflow.task.OpenFeign;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.task.dto.internal.ProjectAccessResponse;
import com.taskflow.task.dto.internal.ProjectInfoResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectClientService {

    private final ProjectClient projectServiceClient;

    @CircuitBreaker(
            name = "projectService",
            fallbackMethod = "getProjectFallback"
    )
    public ProjectInfoResponse getProject(UUID projectId) {

        return projectServiceClient.getProject(projectId);
    }

    public ProjectInfoResponse getProjectFallback(
            UUID projectId,
            Throwable throwable) throws Exception {

        throw new Exception(
                "Project service is currently unavailable");
    }
    
    public ProjectAccessResponse hasAccess(
             UUID projectId,
             UUID userId) {

        return projectServiceClient.hasAccess(projectId,userId);
    }
}