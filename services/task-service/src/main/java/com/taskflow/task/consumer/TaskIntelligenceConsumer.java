package com.taskflow.task.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.taskflow.common.events.TaskIntelligenceGeneratedEvent;
import com.taskflow.task.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskIntelligenceConsumer {

    private final TaskRepository taskRepository;

    @KafkaListener(
            topics = "task-intelligence-generated",
            groupId = "task-service"
    )
    public void consume(
            TaskIntelligenceGeneratedEvent event) {

        log.info(
                "Received AI prediction for task: {}",
                event.getTaskId()
        );

        taskRepository.findById(UUID.fromString(event.getTaskId()))
        .ifPresent(task -> {

            task.setPredictedPriority(
                    event.getPredictedPriority()
            );

            task.setAiConfidence(
                    event.getConfidence()
            );

            task.setAiModelVersion(
                    event.getModelVersion()
            );

            taskRepository.save(task);

            log.info(
                    "AI intelligence saved for task: {}",
                    task.getId()
            );
        });
    }
}