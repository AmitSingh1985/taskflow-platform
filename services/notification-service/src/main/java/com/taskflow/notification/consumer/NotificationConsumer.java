package com.taskflow.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.taskflow.common.constants.KafkaTopics;
import com.taskflow.common.events.TaskCreatedEvent;
import com.taskflow.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.TASK_CREATED,
            groupId = "notification-group")
    public void consume(TaskCreatedEvent event){

        System.out.println("Received Event");

        System.out.println(event);

        notificationService.save(event);

    }

}