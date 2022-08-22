package com.zmv.feature.mappers;

import com.zmv.feature.dto.FeatureDto;
import com.zmv.feature.entities.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeatureMapper {
    @Mapping(source = "feature.id", target = "id")
    @Mapping(source = "feature.createdByUserId", target = "createdByUserId")
    @Mapping(source = "feature.devId", target = "devId")
    @Mapping(source = "feature.featureState", target = "featureState")
    @Mapping(source = "feature.name", target = "name")
    @Mapping(source = "feature.description", target = "description")

    FeatureDto toFeatureDto(Feature feature);

    List<FeatureDto> toFeatureDto(List<Feature> features);
}
