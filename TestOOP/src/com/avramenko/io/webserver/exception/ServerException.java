package com.avramenko.io.webserver.expcetion;

import com.avramenko.io.webserver.entity.StatusCode;

public class ServerException extends RuntimeException {
    private StatusCode statusCode;

    public ServerException(String message, Throwable cause, StatusCode statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
