package com.zvm.clients.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-tasks")
public interface TaskClient {
    @GetMapping(path = "api/v1/tasks/{taskId}/isPresent")
    TaskPresenceResponse isPresent(@PathVariable("taskId") Integer taskId);

    @GetMapping("api/v1/tasks/feature/{featureId}/check")
    public FeatureTasksCompletionResponse isFeatureComplete(@PathVariable("featureId") Integer featureId);
}
