package com.taskflow.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskCreatedEvent(

        UUID taskId,

        UUID projectId,

        UUID assignedUserId,

        String title,

        LocalDateTime createdAt

) {
}