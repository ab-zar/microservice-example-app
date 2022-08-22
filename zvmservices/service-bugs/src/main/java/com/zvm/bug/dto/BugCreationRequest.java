package com.zvm.bug.dto;

public record BugCreationRequest(
        String taskId,
        String bugName,
        String description,
        String createdByUserId
) {
}
