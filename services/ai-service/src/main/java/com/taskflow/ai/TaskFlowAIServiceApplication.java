package com.taskflow.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableFeignClients
@EnableDiscoveryClient
public class TaskFlowAIServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskFlowAIServiceApplication.class,args);
    }

}