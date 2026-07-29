package com.taskflow.project.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID>{

    List<Project> findByOwnerId(UUID ownerId);

    boolean existsByNameAndOwnerId(String name,UUID ownerId);

}