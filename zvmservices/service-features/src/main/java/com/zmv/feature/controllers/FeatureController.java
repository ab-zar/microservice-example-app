package com.zmv.feature.controllers;

import com.zmv.feature.dto.FeatureAssignmentRequest;
import com.zmv.feature.dto.FeatureClosureRequest;
import com.zmv.feature.dto.FeatureCreationRequest;
import com.zmv.feature.dto.FeatureDto;
import com.zmv.feature.services.FeatureService;
import com.zvm.clients.feature.FeaturePresenceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/features")
public record FeatureController(FeatureService featureService) {

    @GetMapping
    public ResponseEntity<List<FeatureDto>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @GetMapping("/{featureId}")
    public ResponseEntity<FeatureDto> getFeature(@PathVariable("featureId") Integer featureId) {
        return ResponseEntity.ok(featureService.getFeatureById(featureId));
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<List<FeatureDto>> getAllFeaturesOfDev(@PathVariable("devId") Integer devId) {
        return ResponseEntity.ok(featureService.getAllFeaturesOfDev(devId));
    }

    @GetMapping("/createdBy/{userId}")
    public ResponseEntity<List<FeatureDto>> getAllFeaturesCreatedByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(featureService.getAllFeaturesCreatedByUser(userId));
    }

    @PutMapping
    public ResponseEntity<FeatureDto> createFeature(@RequestBody FeatureCreationRequest featureCreationRequest) {
        return ResponseEntity.ok(featureService.addFeature(featureCreationRequest));
    }

    @PostMapping("/assign")
    public ResponseEntity<FeatureDto> assignFeatureToDev(@RequestBody FeatureAssignmentRequest featureAssignmentRequest) {
        return ResponseEntity.ok(featureService.assignFeatureToDev(featureAssignmentRequest));
    }

    @GetMapping("/isPresent/{featureId}")
    public FeaturePresenceResponse isPresent(@PathVariable("featureId") Integer featureId) {
        boolean isPresent = featureService.isPresent(featureId);
        return new FeaturePresenceResponse(isPresent);
    }

    @PostMapping("/close")
    public ResponseEntity<FeatureDto> closeFeature(@RequestBody FeatureClosureRequest featureClosureRequest) {
        return ResponseEntity.ok(featureService.closeFeature(featureClosureRequest));
    }
}
