package com.taskflow.task.dto.internal;

import java.util.UUID;

import lombok.Data;

@Data
public class ProjectInfoResponse {

    private UUID id;

    private UUID ownerId;

    private boolean archived;

}