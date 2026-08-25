package com.taskflow.task.exception;
public class TaskNotCreatedException
        extends RuntimeException {

    public TaskNotCreatedException(String message) {
        super(message);
    }
}