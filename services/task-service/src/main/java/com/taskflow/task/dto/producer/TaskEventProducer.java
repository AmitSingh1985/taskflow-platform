package com.taskflow.task.dto.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.taskflow.common.constants.KafkaTopics;
import com.taskflow.common.events.TaskCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;

    public void publishTaskCreated(TaskCreatedEvent event) {

        kafkaTemplate.send(
                KafkaTopics.TASK_CREATED,
                event.taskId().toString(),
                event);

    }

}