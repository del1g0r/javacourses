package com.avramenko.io.webserver.exception;

public class StatusCodeNotFoundException extends RuntimeException {
    public StatusCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
