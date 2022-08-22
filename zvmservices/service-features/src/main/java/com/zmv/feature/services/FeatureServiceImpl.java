package com.zmv.feature.services;

import com.zmv.feature.dto.FeatureAssignmentRequest;
import com.zmv.feature.dto.FeatureClosureRequest;
import com.zmv.feature.dto.FeatureCreationRequest;
import com.zmv.feature.dto.FeatureDto;
import com.zmv.feature.entities.Feature;
import com.zmv.feature.enums.FeatureState;
import com.zmv.feature.mappers.FeatureMapper;
import com.zmv.feature.repsitories.FeatureRepository;
import com.zvm.clients.feature.FeatureIsIncompleteException;
import com.zvm.clients.feature.FeatureNotFoundException;
import com.zvm.clients.task.FeatureTasksCompletionResponse;
import com.zvm.clients.task.TaskClient;
import com.zvm.clients.user.UserClient;
import com.zvm.clients.user.UserNotFoundException;
import com.zvm.clients.user.UserPresenceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;
    private final TaskClient taskClient;
    private final UserClient userClient;

    @Override
    public List<FeatureDto> getAllFeatures() {
        List<Feature> features = featureRepository.findAll();
        return featureMapper.toFeatureDto(features);
    }

    @Override
    public FeatureDto getFeatureById(Integer id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new FeatureNotFoundException("No such feature!", HttpStatus.BAD_REQUEST));
        return featureMapper.toFeatureDto(feature);
    }

    @Override
    public FeatureDto addFeature(FeatureCreationRequest featureCreationRequest) {
        Feature feature = Feature.builder()
                .name(featureCreationRequest.name())
                .createdByUserId(Integer.valueOf(featureCreationRequest.createdByUserId()))
                .description(featureCreationRequest.description())
                .featureState(FeatureState.OPEN)
                .build();
        featureRepository.saveAndFlush(feature);
        return featureMapper.toFeatureDto(feature);
    }

    @Override
    public Boolean isPresent(Integer featureId) {
        Boolean isPresent = featureRepository.existsById(featureId);
        return isPresent;
    }

    @Override
    public List<FeatureDto> getAllFeaturesOfDev(Integer devId) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(devId);
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        List<Feature> features = featureRepository.findAllByDevId(devId);
        return featureMapper.toFeatureDto(features);
    }

    @Override
    public List<FeatureDto> getAllFeaturesCreatedByUser(Integer userId) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(userId);
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        List<Feature> features = featureRepository.findAllByCreatedByUserId(userId);
        return featureMapper.toFeatureDto(features);
    }

    @Override
    public FeatureDto assignFeatureToDev(FeatureAssignmentRequest featureAssignmentRequest) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(Integer.valueOf(featureAssignmentRequest.devId()));
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        Feature feature = featureRepository.findById(Integer.valueOf(featureAssignmentRequest.featureId()))
                .orElseThrow(() -> new FeatureNotFoundException("No such feature!", HttpStatus.BAD_REQUEST));
        feature.setDevId(Integer.valueOf(featureAssignmentRequest.devId()));
        featureRepository.saveAndFlush(feature);
        return featureMapper.toFeatureDto(feature);
    }

    @Override
    public FeatureDto closeFeature(FeatureClosureRequest featureClosureRequest) {
        Feature feature = featureRepository.findById(Integer.valueOf(featureClosureRequest.featureId()))
                .orElseThrow(() -> new FeatureNotFoundException("No such feature!", HttpStatus.BAD_REQUEST));
        FeatureTasksCompletionResponse featureTasksCompletionResponse = taskClient.isFeatureComplete(Integer.valueOf(featureClosureRequest.featureId()));
        if(!featureTasksCompletionResponse.isComplete()) {
            throw new FeatureIsIncompleteException("Cannot close the feature. Not all task are complete!");
        }
        feature.setFeatureState(FeatureState.COMPLETED);
        featureRepository.saveAndFlush(feature);
        return featureMapper.toFeatureDto(feature);
    }


}
