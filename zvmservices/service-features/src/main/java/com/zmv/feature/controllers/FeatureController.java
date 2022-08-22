package com.zmv.feature.controllers;

import com.zmv.feature.dto.FeatureAssignmentRequest;
import com.zmv.feature.dto.FeatureClosureRequest;
import com.zmv.feature.dto.FeatureCreationRequest;
import com.zmv.feature.dto.FeatureDto;
import com.zmv.feature.services.FeatureService;
import com.zvm.clients.feature.FeaturePresenceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/features")
public record FeatureController(FeatureService featureService) {

    @GetMapping
    public ResponseEntity<List<FeatureDto>> getAllFeatures() {
        log.info("Getting all features");
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @GetMapping("/{featureId}/get")
    public ResponseEntity<FeatureDto> getFeature(@PathVariable("featureId") Integer featureId) {
        log.info("Getting feature by id {}", featureId);
        return ResponseEntity.ok(featureService.getFeatureById(featureId));
    }

    @GetMapping("/dev/{devId}/getFeatures")
    public ResponseEntity<List<FeatureDto>> getAllFeaturesOfDev(@PathVariable("devId") Integer devId) {
        log.info("Getting all features assigned to Developer {}", devId);
        return ResponseEntity.ok(featureService.getAllFeaturesOfDev(devId));
    }

    @GetMapping("/createdBy/{userId}/getFeatures")
    public ResponseEntity<List<FeatureDto>> getAllFeaturesCreatedByUser(@PathVariable("userId") Integer userId) {
        log.info("Getting all features created by Manager {}", userId);
        return ResponseEntity.ok(featureService.getAllFeaturesCreatedByUser(userId));
    }

    @PutMapping
    public ResponseEntity<FeatureDto> createFeature(@RequestBody FeatureCreationRequest featureCreationRequest) {
        log.info("Creating new feature {}", featureCreationRequest);
        return ResponseEntity.ok(featureService.addFeature(featureCreationRequest));
    }

    @PostMapping("/assign")
    public ResponseEntity<FeatureDto> assignFeatureToDev(@RequestBody FeatureAssignmentRequest featureAssignmentRequest) {
        log.info("Assigning existing feature {} to Developer {}", featureAssignmentRequest.featureId(), featureAssignmentRequest.devId());
        return ResponseEntity.ok(featureService.assignFeatureToDev(featureAssignmentRequest));
    }

    @GetMapping("/{featureId}/isPresent")
    public FeaturePresenceResponse isPresent(@PathVariable("featureId") Integer featureId) {
        log.info("Checking if feature {} exists", featureId);
        return new FeaturePresenceResponse(featureService.isPresent(featureId));
    }

    @PostMapping("/close")
    public ResponseEntity<FeatureDto> closeFeature(@RequestBody FeatureClosureRequest featureClosureRequest) {
        log.info("Trying to close feature {}", featureClosureRequest.featureId());
        return ResponseEntity.ok(featureService.closeFeature(featureClosureRequest));
    }
}
