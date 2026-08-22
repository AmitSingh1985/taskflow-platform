package com.taskflow.notification.service;

import com.taskflow.common.events.TaskCreatedEvent;

public interface NotificationService {

    void save(TaskCreatedEvent event);

}