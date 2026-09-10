package com.taskflow.search.converter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ReadingConverter
public class ElasticsearchLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
            DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss.SSSX][yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd HH:mm:ss]");
    
    private static final DateTimeFormatter DATE_ONLY_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            // 1. Try to read full timestamp first
            return LocalDateTime.parse(source, TIMESTAMP_FORMATTER);
        } catch (DateTimeParseException e) {
            // 2. Fallback: Parse plain date and append midnight
            return LocalDate.parse(source, DATE_ONLY_FORMATTER).atStartOfDay();
        }
    }
}
