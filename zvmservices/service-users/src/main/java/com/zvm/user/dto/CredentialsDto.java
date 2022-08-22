package com.zvm.user.dto;

public record CredentialsDto(
        String login,
        char[] password
) {
}
