package com.zvm.task.dto;

public record TaskAssignmentRequest(
        String taskId,
        String devId,
        String createdByUserId,
        String testerId
) {
}
