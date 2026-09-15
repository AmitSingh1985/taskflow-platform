package com.taskflow.ai.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPredictionRequest {

    private double complexity;

    private double urgency;

    private double dependencyCount;

    private double estimatedHours;

    private double descriptionLength;
}