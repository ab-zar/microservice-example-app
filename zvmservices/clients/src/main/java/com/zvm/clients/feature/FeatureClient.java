package com.zvm.clients.feature;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-features")
public interface FeatureClient {
    @GetMapping("api/v1/features/{featureId}/isPresent")
    FeaturePresenceResponse isPresent(@PathVariable("featureId") Integer featureId);
}
