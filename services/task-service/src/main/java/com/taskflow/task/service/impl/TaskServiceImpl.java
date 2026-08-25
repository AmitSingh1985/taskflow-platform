package com.taskflow.task.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.common.events.TaskCreatedEvent;
import com.taskflow.task.OpenFeign.ProjectClientService;
import com.taskflow.task.dto.internal.ProjectAccessResponse;
import com.taskflow.task.dto.internal.ProjectInfoResponse;
import com.taskflow.task.dto.producer.TaskEventProducer;
import com.taskflow.task.entity.Task;
import com.taskflow.task.enums.TaskStatus;
import com.taskflow.task.exception.TaskNotCreatedException;
import com.taskflow.task.repository.TaskRepository;
import com.taskflow.task.request.CreateTaskRequest;
import com.taskflow.task.request.UpdateTaskRequest;
import com.taskflow.task.response.TaskResponse;
import com.taskflow.task.service.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

	private final TaskRepository repository;

	private final ProjectClientService projectClient;

	private final TaskEventProducer taskEventProducer;

	@Override
	@Transactional
	public TaskResponse create(CreateTaskRequest request, UUID userId) {

		ProjectInfoResponse project = projectClient.getProject(request.getProjectId());

		if (project.isArchived()) {
			throw new TaskNotCreatedException("Project archived");
		}

		ProjectAccessResponse access = projectClient.hasAccess(request.getProjectId(), userId);

		if (!access.isAuthorized()) {
			throw new TaskNotCreatedException("Access denied.");
		}
		Task task = null;

		task = Task.builder().projectId(request.getProjectId()).title(request.getTitle())
				.description(request.getDescription()).priority(request.getPriority())
				.assignedTo(request.getAssignedTo()).createdBy(userId).dueDate(request.getDueDate())
				.estimatedHours(request.getEstimatedHours()).status(TaskStatus.TODO).build();

		task = repository.save(task);
		TaskCreatedEvent event = new TaskCreatedEvent(

				task.getId(),

				task.getProjectId(),

				task.getAssignedTo(),

				task.getTitle(),

				task.getCreatedAt()

		);

		taskEventProducer.publishTaskCreated(event);

		return map(task);
	}

	@Override
	public TaskResponse update(UUID taskId, UpdateTaskRequest request, UUID userId) {

		Task task = repository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
		ProjectAccessResponse access = projectClient.hasAccess(task.getProjectId(), userId);

		if (!access.isAuthorized()) {
			throw new RuntimeException("Access denied");
		}

		// TODO:
		// Validate that the logged-in user has access to this task's project
		// using Project Service before allowing the update.

		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setPriority(request.getPriority());
		task.setAssignedTo(request.getAssignedTo());
		task.setDueDate(request.getDueDate());
		task.setEstimatedHours(request.getEstimatedHours());

		task = repository.save(task);

		return map(task);
	}

	@Override
	public void delete(UUID taskId, UUID userId) {

		Task task = repository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
		ProjectAccessResponse access = projectClient.hasAccess(task.getProjectId(), userId);

		if (!access.isAuthorized()) {
			throw new RuntimeException("Access denied");
		}

		// TODO:
		// Validate access using Project Service.

		repository.delete(task);
	}

	@Override
	public TaskResponse getTask(UUID taskId) {

		Task task = repository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

		return map(task);
	}

	@Override
	public List<TaskResponse> getProjectTasks(UUID projectId) {

		return repository.findByProjectId(projectId).stream().map(this::map).toList();
	}

	private TaskResponse map(Task task) {

		return TaskResponse.builder().id(task.getId()).projectId(task.getProjectId()).title(task.getTitle())
				.description(task.getDescription()).status(task.getStatus()).priority(task.getPriority())
				.assignedTo(task.getAssignedTo()).createdBy(task.getCreatedBy()).dueDate(task.getDueDate())
				.estimatedHours(task.getEstimatedHours()).build();
	}
}