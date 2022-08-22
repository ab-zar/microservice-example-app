package com.zvm.clients.bug;

import org.springframework.http.HttpStatus;

public class BugNotFoundException extends RuntimeException{

    private final HttpStatus status;
    public BugNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
