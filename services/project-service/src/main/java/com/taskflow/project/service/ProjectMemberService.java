package com.taskflow.project.service;

import java.util.UUID;

import com.taskflow.project.enums.ProjectRole;
import com.taskflow.project.request.AddProjectMemberRequest;
import com.taskflow.project.response.ProjectMemberResponse;

public interface ProjectMemberService {

    ProjectMemberResponse addMember(UUID projectId,
                                    UUID loggedInUser,
                                    AddProjectMemberRequest request);

    ProjectMemberResponse updateMemberRole(UUID projectId,
                                           UUID loggedInUser,
                                           UUID memberUserId,
                                           ProjectRole role);

    void removeMember(UUID projectId,
                      UUID loggedInUser,
                      UUID memberUserId);

}