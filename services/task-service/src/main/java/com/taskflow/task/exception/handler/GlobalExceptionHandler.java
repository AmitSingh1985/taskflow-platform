package com.taskflow.task.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taskflow.task.exception.ProjectNotFoundException;
import com.taskflow.task.exception.ProjectServiceException;
import com.taskflow.task.exception.ProjectServiceUnavailableException;
import com.taskflow.task.exception.TaskNotCreatedException;
import com.taskflow.task.response.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleProjectServiceUnavailable(
            ProjectServiceUnavailableException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "PROJECT_SERVICE_UNAVAILABLE",
                ex.getMessage(),LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProjectNotFound(
            ProjectNotFoundException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "PROJECT_NOT_FOUND",
                ex.getMessage(),LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ProjectServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleProjectServiceError(
            ProjectServiceException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                "PROJECT_SERVICE_ERROR",
                ex.getMessage(),LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(response);
    }
    @ExceptionHandler(TaskNotCreatedException.class)
    public ResponseEntity<ApiErrorResponse> handleTaskNotCreatedError(
            TaskNotCreatedException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "TASK_NOT_CREATED",
                ex.getMessage(),LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}