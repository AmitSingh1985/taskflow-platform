package com.taskflow.search.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.taskflow.search.document.ProjectDocument;

public interface ProjectSearchRepository
        extends ElasticsearchRepository<ProjectDocument,UUID>{
	
	//For elastic search later need to move to Search Service
	List<ProjectDocument> findByNameContainingOrDescriptionContaining(
            String name,
            String description
    );

}