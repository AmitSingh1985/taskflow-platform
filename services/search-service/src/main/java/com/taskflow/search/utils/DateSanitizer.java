package com.taskflow.search.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateSanitizer {

	private static final DateTimeFormatter ES_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * Converts a LocalDateTime object into an Elasticsearch-compliant date string.
	 * Handles missing values safely.
	 */
	public static String toElasticString(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return localDateTime.format(ES_FORMATTER);
	}
}
