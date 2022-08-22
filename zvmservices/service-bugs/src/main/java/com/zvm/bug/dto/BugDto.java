package com.zvm.bug.dto;

public record BugDto(
        Integer id,
        Integer taskId,
        Integer createdByUserId,
        String name,
        String description,
        String state
) {

}
