package com.example.Event_mgmt_project.exceptionHandling;

public class AttendeeNotFoundException extends RuntimeException {

    public AttendeeNotFoundException(String message) {
        super(message);
    }

    public AttendeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
