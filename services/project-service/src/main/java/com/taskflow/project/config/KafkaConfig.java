package com.taskflow.project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taskflow.common.constants.KafkaTopics;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic projectCreatedTopic() {
        return new NewTopic(
                KafkaTopics.PROJECT_CREATED,
                1,
                (short) 1);
    }

    @Bean
    public NewTopic projectUpdatedTopic() {
        return new NewTopic(
                KafkaTopics.PROJECT_UPDATED,
                1,
                (short) 1);
    }

    @Bean
    public NewTopic projectDeletedTopic() {
        return new NewTopic(
                KafkaTopics.PROJECT_DELETED,
                1,
                (short) 1);
    }

    // Your existing task topics can remain here if they're currently in this service.
}