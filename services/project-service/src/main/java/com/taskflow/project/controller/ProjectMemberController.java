package com.taskflow.project.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.project.enums.ProjectRole;
import com.taskflow.project.request.AddProjectMemberRequest;
import com.taskflow.project.response.ProjectMemberResponse;
import com.taskflow.project.service.ProjectMemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectMemberController {

	private final ProjectMemberService projectMemberService;

	@PostMapping("/{projectId}/members")
	public ProjectMemberResponse addMember(@PathVariable UUID projectId, @RequestHeader("X-User-Id") UUID loggedInUser,
			@Valid @RequestBody AddProjectMemberRequest request) {

		return projectMemberService.addMember(projectId, loggedInUser, request);
	}

	@PutMapping("/{projectId}/members/{userId}/role")
	public ProjectMemberResponse updateRole(@PathVariable UUID projectId, @PathVariable UUID userId,
			@RequestHeader("X-User-Id") UUID loggedInUser, @RequestParam ProjectRole role) {

		return projectMemberService.updateMemberRole(projectId, loggedInUser, userId, role);
	}

	@DeleteMapping("/{projectId}/members/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeMember(@PathVariable UUID projectId, @PathVariable UUID userId,
			@RequestHeader("X-User-Id") UUID loggedInUser) {

		projectMemberService.removeMember(projectId, loggedInUser, userId);
	}
}