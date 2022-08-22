package com.zvm.clients.bug;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-bugs")
public interface BugClient {
    @GetMapping(path = "api/v1/bugs/isResolved/{bugId}")
    BugCheckResponse isResolved(@PathVariable("bugId") Integer bugId);

    @GetMapping("api/v1/bugs/task/check/{taskId}")
    TaskBugCheckResponse hasBug(@PathVariable("taskId") Integer taskId);
}
