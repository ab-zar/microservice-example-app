package com.zmv.feature.dto;

public record FeatureDto(
        Integer id,
        Integer createdByUserId,
        Integer devId,
        String featureState,
        String name,
        String description
) { }
