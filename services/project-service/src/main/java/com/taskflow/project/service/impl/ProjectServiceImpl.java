package com.taskflow.project.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.taskflow.project.document.ProjectDocument;
import com.taskflow.project.dto.internal.ProjectAccessResponse;
import com.taskflow.project.dto.internal.ProjectInfoResponse;
import com.taskflow.project.entity.Project;
import com.taskflow.project.enums.ProjectStatus;
import com.taskflow.project.repository.ProjectMemberRepository;
import com.taskflow.project.repository.ProjectRepository;
import com.taskflow.project.repository.ProjectSearchRepository;
import com.taskflow.project.request.CreateProjectRequest;
import com.taskflow.project.request.UpdateProjectRequest;
import com.taskflow.project.response.ProjectResponse;
import com.taskflow.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository repository;

	private final ProjectMemberRepository memberRepository;
	
	private final ProjectSearchRepository projectSearchRepository;
	
	@Override
	public ProjectResponse create(CreateProjectRequest request, UUID ownerId) {

		if (repository.existsByNameAndOwnerId(request.getName(), ownerId)) {
			throw new RuntimeException("Project already exists.");
		}

		Project project = Project.builder().name(request.getName()).description(request.getDescription())
				.ownerId(ownerId).status(ProjectStatus.ACTIVE).startDate(request.getStartDate())
				.endDate(request.getEndDate()).build();

		project = repository.save(project);
		if(null != project) {
			projectSearchRepository.save(
			        mapSearchDocument(project)
			);
		}

		return map(project);

	}

	@Override
	public ProjectResponse getById(UUID projectId, UUID loggedInUser) {

		Project project = getProjectFromCache(projectId);

		if (project.isArchived()) {
			throw new RuntimeException("Project archived");
		}

		if (!project.getOwnerId().equals(loggedInUser)) {

			boolean member = memberRepository.existsByProjectIdAndUserId(projectId, loggedInUser);

			if (!member) {
				throw new RuntimeException("Access denied");
			}
		}
		
		return map(project);
	}
	
	@Cacheable(value = "projects", key = "#projectId")
	public Project getProjectFromCache(UUID projectId) {

	    return repository.findById(projectId)
	            .orElseThrow(() -> new RuntimeException("Project not found"));
	}

	@Override
	public List<ProjectResponse> getMyProjects(UUID ownerId) {

		return repository.findByOwnerId(ownerId).stream().filter(project -> !project.isArchived()).map(this::map)
				.toList();

	}

	@Override
	@CachePut(
	        value = "projects",
	        key = "#projectId")
	public ProjectResponse update(UUID projectId, UUID ownerId, UpdateProjectRequest request) {

		Project project = repository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		if (!project.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Only owner can update project");
		}

		project.setName(request.getName());

		project.setDescription(request.getDescription());

		project.setStartDate(request.getStartDate());

		project.setEndDate(request.getEndDate());

		project = repository.save(project);
		if(null!=project) {
			projectSearchRepository.save(
			        mapSearchDocument(project)
			);
		}

		return map(project);

	}

	@Override
	@CacheEvict(
	        value = "projects",
	        key = "#projectId")
	public void delete(UUID projectId, UUID ownerId) {

		Project project = repository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		if (!project.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Only owner can delete project");
		}

		project.setArchived(true);

		project.setStatus(ProjectStatus.ARCHIVED);

		project = repository.save(project);
		if(null!=project && project.isArchived()) {
			projectSearchRepository.deleteById(project.getId());
		}

	}

	private ProjectResponse map(Project project) {

		return ProjectResponse.builder().id(project.getId()).name(project.getName())
				.description(project.getDescription()).status(project.getStatus()).ownerId(project.getOwnerId())
				.startDate(project.getStartDate()).endDate(project.getEndDate()).build();
	}

	@Override
	public ProjectInfoResponse getProjectInfo(UUID projectId) {

		Project project = repository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		ProjectInfoResponse response = new ProjectInfoResponse();

		response.setId(project.getId());
		response.setOwnerId(project.getOwnerId());
		response.setArchived(project.isArchived());

		return response;
	}

	@Override
	public ProjectAccessResponse hasAccess(UUID projectId, UUID userId) {

		Project project = repository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		boolean authorized = false;

		if (project.getOwnerId().equals(userId)) {
			authorized = true;
		} else {

			authorized = memberRepository.existsByProjectIdAndUserId(projectId, userId);

		}

		ProjectAccessResponse response = new ProjectAccessResponse();

		response.setAuthorized(authorized);

		return response;
	}
	
	public ProjectDocument mapSearchDocument(Project project){

	    return ProjectDocument.builder()

	            .id(project.getId())

	            .name(project.getName())

	            .description(project.getDescription())

	            .status(project.getStatus())

	            .ownerId(project.getOwnerId())

	            .startDate(project.getStartDate())

	            .endDate(project.getEndDate())

	            .build();

	}
	
	@Override
	public List<ProjectDocument> elasticSearch(String keyword){

	    return projectSearchRepository
	            .findByNameContaining(keyword);

	}

}