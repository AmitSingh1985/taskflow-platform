package com.taskflow.task.service;

import java.util.List;
import java.util.UUID;

import com.taskflow.task.request.CreateTaskRequest;
import com.taskflow.task.request.UpdateTaskRequest;
import com.taskflow.task.response.TaskResponse;

public interface TaskService {

    TaskResponse create(CreateTaskRequest request,
                        UUID userId);

    TaskResponse update(UUID taskId,
                        UpdateTaskRequest request,
                        UUID userId);

    void delete(UUID taskId,
                UUID userId);

    TaskResponse getTask(UUID taskId);

    List<TaskResponse> getProjectTasks(UUID projectId);

}