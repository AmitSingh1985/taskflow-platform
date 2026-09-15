package com.taskflow.task.ai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.taskflow.task.ai.dto.AiTaskDraft;

@FeignClient(name = "ai-service")
public interface AiServiceClient {

    @PostMapping("/api/ai/tasks/draft")
    AiTaskDraft generateTaskDraft(
            @RequestBody String request
    );
}