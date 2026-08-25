package com.taskflow.task.OpenFeign;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.task.dto.internal.ProjectAccessResponse;
import com.taskflow.task.dto.internal.ProjectInfoResponse;
import com.taskflow.task.exception.ProjectNotFoundException;
import com.taskflow.task.exception.ProjectServiceException;
import com.taskflow.task.exception.ProjectServiceUnavailableException;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectClientService {

    private final ProjectClient projectServiceClient;
    
    @Retry(name = "getProjectService")
    @CircuitBreaker(
            name = "getProjectService",
            fallbackMethod = "getProjectServiceFallback"
    )
    public ProjectInfoResponse getProject(UUID projectId) {

        return projectServiceClient.getProject(projectId);
    }

    public ProjectInfoResponse getProjectServiceFallback(
            UUID projectId,
            Throwable throwable) {

        log.error(
                "Project Service call failed. projectId={}, error={}",
                projectId,
                throwable.getMessage(),
                throwable
        );

        if (throwable instanceof FeignException.NotFound) {

            throw new ProjectNotFoundException(
                    "Project not found: " + projectId
            );
        }

        if (throwable instanceof FeignException.BadRequest) {

            throw new ProjectServiceException(
                    "Invalid request sent to Project Service"
            );
        }

        if (throwable instanceof FeignException.Unauthorized) {

            throw new ProjectServiceException(
                    "Unauthorized access to Project Service"
            );
        }

        if (throwable instanceof FeignException.Forbidden) {

            throw new ProjectServiceException(
                    "Access denied by Project Service"
            );
        }

        if (throwable instanceof FeignException.ServiceUnavailable) {

            throw new ProjectServiceUnavailableException(
                    "Project Service is currently unavailable"
            );
        }

        if (throwable instanceof FeignException) {

            throw new ProjectServiceUnavailableException(
                    "Unable to communicate with Project Service"
            );
        }

        throw new ProjectServiceUnavailableException(
                "Project Service is currently unavailable"
        );
    }
    
    public ProjectAccessResponse hasAccess(
             UUID projectId,
             UUID userId) {

        return projectServiceClient.hasAccess(projectId,userId);
    }
}