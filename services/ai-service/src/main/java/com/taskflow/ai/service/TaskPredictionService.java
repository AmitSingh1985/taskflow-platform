package com.taskflow.ai.service;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import com.taskflow.ai.model.TaskPriorityModel;
import com.taskflow.ai.request.TaskPredictionRequest;
import com.taskflow.ai.response.TaskPredictionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskPredictionService {

    private final TaskPriorityModel taskPriorityModel;

    public TaskPredictionResponse predict(
            TaskPredictionRequest request) {

        INDArray input = Nd4j.create(
                new double[][]{
                        {
                                request.getComplexity(),
                                request.getUrgency(),
                                request.getDependencyCount(),
                                request.getEstimatedHours(),
                                request.getDescriptionLength()
                        }
                }
        );

        INDArray output =
                taskPriorityModel
                        .getModel()
                        .output(input);

        int predictedClass =
                Nd4j.argMax(output, 1).getInt(0);

        double confidence =
                output.getDouble(0, predictedClass);

        String priority =
                switch (predictedClass) {
                    case 0 -> "LOW";
                    case 1 -> "MEDIUM";
                    case 2 -> "HIGH";
                    default ->
                            throw new IllegalStateException(
                                    "Unknown prediction"
                            );
                };

        return TaskPredictionResponse.builder()
                .predictedPriority(priority)
                .confidence(confidence)
                .modelVersion("task-priority-v1")
                .build();
    }
}