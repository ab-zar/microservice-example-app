package com.zvm.user.dto;

public record UserCreationRequest(
        String login,
        char[] password,
        String role
){
}
