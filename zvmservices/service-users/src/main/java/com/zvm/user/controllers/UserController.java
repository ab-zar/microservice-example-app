package com.zvm.user.controllers;

import com.zvm.clients.user.UserPresenceResponse;
import com.zvm.user.dto.UserCreationRequest;
import com.zvm.user.dto.UserDto;
import com.zvm.user.services.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("UserController")
@Slf4j
@RequestMapping("api/v1/users")
public record UserController(UserService userService) {

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }

    @GetMapping("/{userId}/get")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId) {
        log.info("Retrieving user userId {}", userId);
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/{userId}/isPresent")
    public UserPresenceResponse isPresent(@PathVariable("userId") Integer userId) {
        log.info("User existence check by userId {}", userId);
        return new UserPresenceResponse(userService.isPresent(userId));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<UserDto> signIn(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        return ResponseEntity.ok(userService.validateToken(token));
    }
}
