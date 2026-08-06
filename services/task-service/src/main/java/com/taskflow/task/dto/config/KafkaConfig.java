package com.taskflow.task.dto.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taskflow.common.constants.KafkaTopics;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic taskCreatedTopic() {
        return new NewTopic(
                KafkaTopics.TASK_CREATED,
                1,
                (short) 1);
    }

    @Bean
    public NewTopic taskUpdatedTopic() {
        return new NewTopic(
                KafkaTopics.TASK_UPDATED,
                1,
                (short) 1);
    }

    @Bean
    public NewTopic taskDeletedTopic() {
        return new NewTopic(
                KafkaTopics.TASK_DELETED,
                1,
                (short) 1);
    }

}