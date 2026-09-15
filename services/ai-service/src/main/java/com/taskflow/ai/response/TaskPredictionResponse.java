package com.taskflow.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaskPredictionResponse {

    private String predictedPriority;

    private double confidence;

    private String modelVersion;
}