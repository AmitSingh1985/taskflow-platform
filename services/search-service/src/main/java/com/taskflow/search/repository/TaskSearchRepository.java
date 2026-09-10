package com.taskflow.search.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.taskflow.search.document.TaskDocument;

public interface TaskSearchRepository
        extends ElasticsearchRepository<TaskDocument,UUID>{
	
	//For elastic search later need to move to Search Service
	List<TaskDocument> findByTitle(
            String keyword);

}