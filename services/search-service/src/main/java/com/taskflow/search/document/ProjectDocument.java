package com.taskflow.search.document;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
@Document(indexName = "projects")
public class ProjectDocument {

	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String name;

	@Field(type = FieldType.Text)
	private String description;

	@Field(type = FieldType.Keyword)
	private String status;

	@Field(type = FieldType.Keyword)
	private String ownerId;

	@Field(type = FieldType.Date, format = DateFormat.date)
	private LocalDate startDate;

	@Field(type = FieldType.Date, format = DateFormat.date)
	private LocalDate endDate;

}