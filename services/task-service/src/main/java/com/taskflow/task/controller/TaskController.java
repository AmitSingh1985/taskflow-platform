package com.taskflow.task.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.task.request.CreateTaskRequest;
import com.taskflow.task.response.TaskResponse;
import com.taskflow.task.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(
            @RequestBody @Valid CreateTaskRequest request,
            @RequestHeader("X-User-Id") UUID userId){

        return service.create(request,userId);

    }

}