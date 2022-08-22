package com.zvm.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-users")
public interface UserClient {
    @GetMapping("api/v1/users/{userId}/isPresent")
    UserPresenceResponse isPresent(@PathVariable("userId") Integer userId);
}
