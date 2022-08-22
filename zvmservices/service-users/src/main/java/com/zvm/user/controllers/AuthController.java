package com.zvm.user.controllers;

import com.zvm.user.dto.CredentialsDto;
import com.zvm.user.dto.UserDto;
import com.zvm.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1")
public record AuthController(UserService userService) {

    @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(@RequestBody CredentialsDto credentialsDto) {
        log.info("Trying to login {}", credentialsDto.login());
        return ResponseEntity.ok(userService.signIn(credentialsDto));
    }
}
