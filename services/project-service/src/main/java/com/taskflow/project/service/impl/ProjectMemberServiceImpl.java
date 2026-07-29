package com.taskflow.project.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.taskflow.project.entity.Project;
import com.taskflow.project.entity.ProjectMember;
import com.taskflow.project.enums.ProjectRole;
import com.taskflow.project.repository.ProjectMemberRepository;
import com.taskflow.project.repository.ProjectRepository;
import com.taskflow.project.request.AddProjectMemberRequest;
import com.taskflow.project.response.ProjectMemberResponse;
import com.taskflow.project.service.ProjectMemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

	private final ProjectRepository projectRepository;

	private final ProjectMemberRepository memberRepository;

	@Override
	public ProjectMemberResponse addMember(UUID projectId, UUID loggedInUser, AddProjectMemberRequest request) {

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new RuntimeException("Project not found"));

		if (!project.getOwnerId().equals(loggedInUser)) {
			throw new RuntimeException("Only owner can add members");
		}

		if (memberRepository.existsByProjectIdAndUserId(projectId, request.getUserId())) {

			throw new RuntimeException("User already exists in project");
		}

		ProjectMember member = ProjectMember.builder().projectId(projectId).userId(request.getUserId())
				.role(request.getRole()).build();

		member = memberRepository.save(member);

		return ProjectMemberResponse.builder().id(member.getId()).projectId(member.getProjectId())
				.userId(member.getUserId()).role(member.getRole()).joinedAt(member.getJoinedAt()).build();
	}

	@Override
	public ProjectMemberResponse updateMemberRole(UUID projectId, UUID loggedInUser, UUID memberUserId,
			ProjectRole role) {

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new RuntimeException("Project not found"));

		if (!project.getOwnerId().equals(loggedInUser)) {
			throw new RuntimeException("Only project owner can update roles.");
		}

		if (project.getOwnerId().equals(memberUserId)) {
			throw new RuntimeException("Project owner's role cannot be changed.");
		}

		ProjectMember member = memberRepository.findByProjectIdAndUserId(projectId, memberUserId)
				.orElseThrow(() -> new RuntimeException("Project member not found."));

		member.setRole(role);

		member = memberRepository.save(member);

		return ProjectMemberResponse.builder().id(member.getId()).projectId(member.getProjectId())
				.userId(member.getUserId()).role(member.getRole()).joinedAt(member.getJoinedAt()).build();
	}

	@Override
	public void removeMember(UUID projectId, UUID loggedInUser, UUID memberUserId) {

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new RuntimeException("Project not found"));

		if (!project.getOwnerId().equals(loggedInUser)) {
			throw new RuntimeException("Only project owner can remove members.");
		}

		if (project.getOwnerId().equals(memberUserId)) {
			throw new RuntimeException("Project owner cannot be removed.");
		}

		ProjectMember member = memberRepository.findByProjectIdAndUserId(projectId, memberUserId)
				.orElseThrow(() -> new RuntimeException("Project member not found."));

		memberRepository.delete(member);
	}
}