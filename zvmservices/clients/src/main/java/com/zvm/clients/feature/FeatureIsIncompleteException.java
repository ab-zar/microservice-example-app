package com.zvm.clients.feature;

public class FeatureIsIncompleteException extends RuntimeException{
    public FeatureIsIncompleteException(String message) {
        super(message);
    }
}
