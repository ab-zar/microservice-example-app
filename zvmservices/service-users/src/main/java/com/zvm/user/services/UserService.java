package com.zvm.user.services;

import com.zvm.user.dto.CredentialsDto;
import com.zvm.user.dto.UserCreationRequest;
import com.zvm.user.dto.UserDto;

public interface UserService {

    Boolean isPresent(Integer userId);

    UserDto createUser(UserCreationRequest userCreationRequest);

    UserDto getUser(Integer userId);

    UserDto signIn(CredentialsDto credentialsDto);

    UserDto validateToken(String token);
}
