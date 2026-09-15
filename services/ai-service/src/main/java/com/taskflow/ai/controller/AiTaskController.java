package com.taskflow.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.ai.response.AiTaskDraft;
import com.taskflow.ai.response.AiTaskResponse;
import com.taskflow.ai.service.AiTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiTaskController {

    private final AiTaskService aiTaskService;

    @PostMapping("/tasks/draft")
    public AiTaskDraft generateTask(
            @RequestBody String request) {

        return aiTaskService.generateTask(request);
    }
}