package com.taskflow.notification.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.notification.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification,UUID>{

}