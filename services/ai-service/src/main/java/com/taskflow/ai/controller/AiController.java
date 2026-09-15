package com.taskflow.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.ai.request.TaskPredictionRequest;
import com.taskflow.ai.response.TaskPredictionResponse;
import com.taskflow.ai.service.TaskPredictionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final TaskPredictionService taskPredictionService;

    @PostMapping("/predict/priority")
    public TaskPredictionResponse predictPriority(
            @RequestBody TaskPredictionRequest request) {

        return taskPredictionService.predict(request);
    }
}