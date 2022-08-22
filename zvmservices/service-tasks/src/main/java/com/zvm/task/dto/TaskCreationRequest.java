package com.zvm.task.dto;

public record TaskCreationRequest(
        String createdByUserId,
        String featureId,
        String name,
        String description
) {
}
