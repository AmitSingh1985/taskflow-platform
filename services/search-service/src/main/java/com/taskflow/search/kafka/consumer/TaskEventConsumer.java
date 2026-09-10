package com.taskflow.search.kafka.consumer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.taskflow.common.constants.KafkaTopics;
import com.taskflow.common.events.TaskCreatedEvent;
import com.taskflow.search.document.TaskDocument;
import com.taskflow.search.repository.TaskSearchRepository;
import com.taskflow.search.utils.DateSanitizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventConsumer {

	private final TaskSearchRepository repository;

	@KafkaListener(topics = KafkaTopics.TASK_CREATED, groupId = "search-service")
	public void consumeTaskCreated(TaskCreatedEvent event) {

		log.info("Received TaskCreatedEvent: {}", event.getTaskId());
		LocalDateTime withOutNanoSeconds = null;
		if (null != event.getCreatedAt()) {
			withOutNanoSeconds = event.getCreatedAt().truncatedTo(ChronoUnit.MILLIS);
		}
		TaskDocument document = TaskDocument.builder().id(event.getTaskId()).projectId(event.getProjectId())
				.assignedUserId(event.getAssignedUserId()).title(event.getTitle())// Converts your LocalDateTime to a
																					// full timestamp string format:
																					// "yyyy-MM-dd'T'HH:mm:ss"
				.createdAt(withOutNanoSeconds == null ? ""
						: DateSanitizer.toElasticString(withOutNanoSeconds))
				.status(event.getStatus()).build();

		repository.save(document);

		log.info("Project indexed in Elasticsearch: {}", event.getTaskId());
	}
}