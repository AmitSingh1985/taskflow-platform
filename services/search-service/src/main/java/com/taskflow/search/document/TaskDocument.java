package com.taskflow.search.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "tasks")
public class TaskDocument {

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String taskId;

	@Field(type = FieldType.Keyword)
	private String projectId;

	@Field(type = FieldType.Keyword)
	private String assignedUserId;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Keyword)
	private String status;

	@Field(type = FieldType.Date)
	private String createdAt; // Stores raw data from ES

	// Converter method
	public LocalDateTime getCreatedAtAsDateTime() {
	    if (this.createdAt == null || this.createdAt.isBlank()) return null;
	    // Parse as LocalDateTime if time exists, otherwise as LocalDate + midnight
	    return this.createdAt.contains("T") || this.createdAt.contains(" ") 
	        ? LocalDateTime.parse(this.createdAt.split("Z")[0]) 
	        : LocalDate.parse(this.createdAt).atStartOfDay();
	}

}