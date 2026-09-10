package com.taskflow.project.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.taskflow.common.constants.KafkaTopics;
import com.taskflow.common.events.ProjectCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectEventProducer {

    private final KafkaTemplate<String, ProjectCreatedEvent> kafkaTemplate;

    public void publishProjectCreated(ProjectCreatedEvent event) {

        kafkaTemplate.send(
                KafkaTopics.PROJECT_CREATED,
                event.getProjectId().toString(),
                event);

    }

}