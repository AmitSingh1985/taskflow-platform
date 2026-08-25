package com.taskflow.task.exception;
public class ProjectServiceUnavailableException
        extends RuntimeException {

    public ProjectServiceUnavailableException(String message) {
        super(message);
    }
}