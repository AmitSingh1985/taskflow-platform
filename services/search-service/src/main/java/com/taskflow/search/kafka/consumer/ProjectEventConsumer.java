package com.taskflow.search.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.taskflow.common.constants.KafkaTopics;
import com.taskflow.common.events.ProjectCreatedEvent;
import com.taskflow.search.document.ProjectDocument;
import com.taskflow.search.repository.ProjectSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectEventConsumer {

    private final ProjectSearchRepository repository;

    @KafkaListener(
            topics = KafkaTopics.PROJECT_CREATED,
            groupId = "search-service"
    )
    public void consumeProjectCreated(
            ProjectCreatedEvent event) {

        log.info(
                "Received ProjectCreatedEvent: {}",
                event.getProjectId()
        );

        ProjectDocument document = ProjectDocument.builder()
                .id(event.getProjectId())
                .name(event.getName())
                .description(event.getDescription())
                .status(event.getStatus())
                .ownerId(event.getOwnerId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();

        repository.save(document);

        log.info(
                "Project indexed in Elasticsearch: {}",
                event.getProjectId()
        );
    }
}