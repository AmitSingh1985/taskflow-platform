package com.taskflow.task.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>{

    List<Task> findByProjectId(UUID projectId);

    List<Task> findByAssignedTo(UUID assignedTo);

}