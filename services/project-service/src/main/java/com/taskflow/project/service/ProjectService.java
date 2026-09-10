package com.taskflow.project.service;

import java.util.List;
import java.util.UUID;

import com.taskflow.project.dto.internal.ProjectAccessResponse;
import com.taskflow.project.dto.internal.ProjectInfoResponse;
import com.taskflow.project.request.CreateProjectRequest;
import com.taskflow.project.request.UpdateProjectRequest;
import com.taskflow.project.response.ProjectResponse;

public interface ProjectService {

	ProjectResponse create(CreateProjectRequest request, UUID ownerId);

	ProjectResponse update(UUID projectId, UUID ownerId, UpdateProjectRequest request);

	void delete(UUID projectId, UUID ownerId);

	ProjectResponse getById(UUID projectId, UUID loggedInUser);

	List<ProjectResponse> getMyProjects(UUID ownerId);

	ProjectInfoResponse getProjectInfo(UUID projectId);

	ProjectAccessResponse hasAccess(UUID projectId, UUID userId);
	
	/* List<ProjectDocument> elasticSearch(String keyword); */

}