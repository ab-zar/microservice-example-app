package com.zmv.feature.services;

import com.zmv.feature.dto.FeatureAssignmentRequest;
import com.zmv.feature.dto.FeatureClosureRequest;
import com.zmv.feature.dto.FeatureCreationRequest;
import com.zmv.feature.dto.FeatureDto;

import java.util.List;

public interface FeatureService {

    List<FeatureDto> getAllFeatures();

    FeatureDto getFeatureById(Integer id);

    FeatureDto addFeature(FeatureCreationRequest featureCreationRequest);

    Boolean isPresent(Integer featureId);

    List<FeatureDto> getAllFeaturesOfDev(Integer devId);

    List<FeatureDto> getAllFeaturesCreatedByUser(Integer userId);

    FeatureDto assignFeatureToDev(FeatureAssignmentRequest featureAssignmentRequest);

    FeatureDto closeFeature(FeatureClosureRequest featureClosureRequest);
}
