package com.taskflow.notification.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.common.events.TaskCreatedEvent;
import com.taskflow.notification.entity.Notification;
import com.taskflow.notification.repository.NotificationRepository;
import com.taskflow.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public void save(TaskCreatedEvent event) {

        Notification notification =
                Notification.builder()

                        .id(UUID.randomUUID())

                        .taskId(event.taskId())

                        .projectId(event.projectId())

                        .assignedUserId(event.assignedUserId())

                        .title(event.title())

                        .status("NEW")

                        .createdAt(LocalDateTime.now())

                        .build();

        repository.save(notification);

    }

}