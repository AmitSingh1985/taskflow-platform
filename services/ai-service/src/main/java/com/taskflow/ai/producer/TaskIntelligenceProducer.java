package com.taskflow.ai.producer;

import static com.taskflow.common.constants.KafkaTopics.TASK_INTELLIGENCE_GENERATED;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.taskflow.common.events.TaskIntelligenceGeneratedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskIntelligenceProducer {

    private final KafkaTemplate<String, TaskIntelligenceGeneratedEvent>
            kafkaTemplate;

    public void publish(
            TaskIntelligenceGeneratedEvent event) {

        kafkaTemplate.send(
                TASK_INTELLIGENCE_GENERATED,
                event.getTaskId().toString(),
                event
        );

        log.info(
                "Published TaskIntelligenceGeneratedEvent: taskId={}",
                event.getTaskId()
        );
    }
}