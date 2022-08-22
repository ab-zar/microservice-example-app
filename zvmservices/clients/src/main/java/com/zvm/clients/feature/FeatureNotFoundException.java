package com.zvm.clients.feature;

import org.springframework.http.HttpStatus;

public class FeatureNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public FeatureNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
