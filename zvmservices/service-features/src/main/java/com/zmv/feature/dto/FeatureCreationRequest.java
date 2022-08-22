package com.zmv.feature.dto;

public record FeatureCreationRequest(
        String createdByUserId,
        String devId,
        String name,
        String description
) {
}
