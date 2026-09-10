package com.taskflow.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.search.document.ProjectDocument;
import com.taskflow.search.document.TaskDocument;
import com.taskflow.search.repository.ProjectSearchRepository;
import com.taskflow.search.repository.TaskSearchRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

	private final ProjectSearchRepository projectSearchRepository;
	private final TaskSearchRepository taskSearchRepository;

	@GetMapping("/projects")
	public List<ProjectDocument> searchProjects(@RequestParam String keyword) {

		return projectSearchRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
	}

	@GetMapping("/tasks")
	public List<TaskDocument> searchTasks(@RequestParam String keyword) {

		return taskSearchRepository.findByTitle(keyword);
	}

	@GetMapping("/health")
	public String health() {
		return "Search Service is running";
	}
}