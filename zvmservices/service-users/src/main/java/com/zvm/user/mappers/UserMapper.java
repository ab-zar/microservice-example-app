package com.zvm.user.mappers;

import com.zvm.user.dto.UserDto;
import com.zvm.user.entities.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "user.userRole", target = "userRole")
    @Mapping(source = "token", target = "token")


    UserDto toUserDto(ApplicationUser user, String token);
    UserDto toUserDto(ApplicationUser user);
}
