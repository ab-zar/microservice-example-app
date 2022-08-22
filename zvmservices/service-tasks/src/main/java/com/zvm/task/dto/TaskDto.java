package com.zvm.task.dto;

public record TaskDto(
        Integer id,
        Integer createdByUserId,
        Integer devId,
        Integer testerId,
        Integer featureId,
        String taskState,
        String name,
        String description
) {
}
