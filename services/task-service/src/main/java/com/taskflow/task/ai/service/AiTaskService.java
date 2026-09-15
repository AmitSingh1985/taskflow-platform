package com.taskflow.task.ai.service;

import org.springframework.stereotype.Service;

import com.taskflow.task.ai.client.AiServiceClient;
import com.taskflow.task.ai.dto.AiTaskDraft;
import com.taskflow.task.exception.AiServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiTaskService {

    private final AiServiceClient aiServiceClient;

    @Retry(name = "aiService")
    @CircuitBreaker(
            name = "aiService",
            fallbackMethod = "generateDraftFallback"
    )
    public AiTaskDraft generateDraft(String request) {

        return aiServiceClient.generateTaskDraft(request);
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
}