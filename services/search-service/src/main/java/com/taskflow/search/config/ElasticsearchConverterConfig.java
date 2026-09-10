package com.taskflow.search.config;
import java.util.Arrays;

import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import com.taskflow.search.converter.ElasticsearchLocalDateTimeConverter;

//@Configuration
public class ElasticsearchConverterConfig {

  //  @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
            Arrays.asList(new ElasticsearchLocalDateTimeConverter())
        );
    }
}
