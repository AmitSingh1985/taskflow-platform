package com.taskflow.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.project.request.CreateProjectRequest;
import com.taskflow.project.request.UpdateProjectRequest;
import com.taskflow.project.response.ProjectResponse;
import com.taskflow.project.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Validated @RequestBody CreateProjectRequest request,
            @RequestHeader("X-User-Id") UUID ownerId) {

        return projectService.create(request, ownerId);
    }
    
    @GetMapping
    public List<ProjectResponse> getMyProjects(
            @RequestHeader("X-User-Id") UUID ownerId) {

        return projectService.getMyProjects(ownerId);
    }
    
    @GetMapping("/{projectId}")
    public ProjectResponse getProject(

            @PathVariable UUID projectId,

            @RequestHeader("X-User-Id") UUID userId) {

        return projectService.getById(projectId,
                userId);
    }
    
    @PutMapping("/{projectId}")
    public ProjectResponse update(

            @PathVariable UUID projectId,

            @RequestHeader("X-User-Id") UUID ownerId,

            @Valid
            @RequestBody UpdateProjectRequest request) {

        return projectService.update(projectId,
                ownerId,
                request);
    }
    
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(

            @PathVariable UUID projectId,

            @RequestHeader("X-User-Id") UUID ownerId) {

    	projectService.delete(projectId,
                ownerId);
    }
    
    
	/*
	 * @GetMapping("/search") public List<ProjectDocument> search(
	 * 
	 * @RequestParam String keyword){
	 * 
	 * return projectService.elasticSearch(keyword);
	 * 
	 * }
	 */

}