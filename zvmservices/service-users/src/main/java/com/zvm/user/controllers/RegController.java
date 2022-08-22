package com.zvm.user.controllers;

import com.zvm.user.dto.UserCreationRequest;
import com.zvm.user.dto.UserDto;
import com.zvm.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1")
public record RegController(UserService userService) {

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody UserCreationRequest userCreationRequest) {
        log.info("Creating new user {}", userCreationRequest.login());
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }
}
