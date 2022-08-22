package com.zvm.clients.bug;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-bugs")
public interface BugClient {
    @GetMapping(path = "api/v1/bugs/{bugId}/isResolved")
    BugCheckResponse isResolved(@PathVariable("bugId") Integer bugId);

    @GetMapping("api/v1/bugs/task/{taskId}/check")
    TaskBugCheckResponse hasBug(@PathVariable("taskId") Integer taskId);
}
