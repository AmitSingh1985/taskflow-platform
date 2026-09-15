package com.taskflow.task.ai.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.task.ai.client.AiServiceClient;
import com.taskflow.task.ai.dto.AiTaskDraft;
import com.taskflow.task.enums.TaskPriority;
import com.taskflow.task.exception.AiServiceUnavailableException;
import com.taskflow.task.request.CreateTaskRequest;
import com.taskflow.task.response.TaskResponse;
import com.taskflow.task.service.TaskService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiTaskCreationService {

    private final AiServiceClient aiServiceClient;
    private final TaskService taskService;
    
    
    @Retry(name = "aiService")
    @CircuitBreaker(
            name = "aiService",
            fallbackMethod = "generateDraftFallback"
    )
    public TaskResponse createFromNaturalLanguage(
            UUID projectId,
            UUID assignedId,
            UUID userId,
            String prompt
    ) {

        // 1. Ask Ollama to understand the request
        AiTaskDraft draft =
                aiServiceClient.generateTaskDraft(
                        prompt
                );

        // 2. Convert AI result to normal request
        CreateTaskRequest taskRequest =
                convertToCreateTaskRequest(
                        projectId,
                        assignedId,
                        draft
                );

        // 3. Use normal task creation flow
        return taskService.create(
                
                taskRequest,userId
        );
    }
    
    public AiTaskDraft generateDraftFallback(
            String request,
            Throwable throwable
    ) {
    	log.error("AI task generation is temporarily unavailable",throwable);
        throw new AiServiceUnavailableException(
                "AI task generation is temporarily unavailable"
        );
    }

    private CreateTaskRequest convertToCreateTaskRequest(
            UUID projectId,
            UUID assigneeId,
            AiTaskDraft draft
    ) {

        CreateTaskRequest request =
                new CreateTaskRequest();

        request.setProjectId(projectId);
        request.setAssignedTo(assigneeId);

        request.setTitle(draft.getTitle());
        request.setDescription(draft.getDescription());
        request.setPriority(TaskPriority.valueOf(draft.getPriority()));

        request.setComplexity(draft.getComplexity());
        request.setUrgency(draft.getUrgency());

        request.setDependencyCount(
                draft.getDependencyCount()
        );

        request.setEstimatedHours(
                draft.getEstimatedHours()
        );

        return request;
    }
}