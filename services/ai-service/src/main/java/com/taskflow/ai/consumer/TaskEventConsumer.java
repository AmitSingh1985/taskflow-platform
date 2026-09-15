package com.taskflow.ai.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.taskflow.ai.producer.TaskIntelligenceProducer;
import com.taskflow.ai.request.TaskPredictionRequest;
import com.taskflow.ai.response.TaskPredictionResponse;
import com.taskflow.ai.service.TaskPredictionService;
import com.taskflow.common.events.TaskCreatedEvent;
import com.taskflow.common.events.TaskIntelligenceGeneratedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventConsumer {

    private final TaskPredictionService predictionService;

    private final TaskIntelligenceProducer
            taskIntelligenceProducer;

    @KafkaListener(
            topics = "task-created",
            groupId = "ai-service"
    )
    public void consume(TaskCreatedEvent event) {

        log.info(
                "AI Service received task: {}",
                event.getTaskId()
        );

        TaskPredictionRequest request =
                new TaskPredictionRequest(
                        event.getComplexity(),
                        event.getUrgency(),
                        event.getDependencyCount(),
                        event.getEstimatedHours(),
                        event.getDescription() == null
                                ? 0
                                : event.getDescription().length()
                );

        TaskPredictionResponse prediction =
                predictionService.predict(request);

        log.info(
                "AI prediction: task={}, priority={}, confidence={}",
                event.getTaskId(),
                prediction.getPredictedPriority(),
                prediction.getConfidence()
        );

        TaskIntelligenceGeneratedEvent intelligenceEvent =
                TaskIntelligenceGeneratedEvent.builder()
                        .taskId(event.getTaskId())
                        .predictedPriority(
                                prediction.getPredictedPriority()
                        )
                        .confidence(
                                prediction.getConfidence()
                        )
                        .modelVersion(
                                prediction.getModelVersion()
                        )
                        .build();

        taskIntelligenceProducer.publish(
                intelligenceEvent
        );
    }
}