package com.zvm.user.mappers;

import com.zvm.user.dto.UserDto;
import com.zvm.user.entities.ApplicationUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-22T13:43:23+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(ApplicationUser user, String token) {
        if ( user == null && token == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        if ( user != null ) {
            userDto.id( user.getId() );
            userDto.login( user.getLogin() );
            if ( user.getUserRole() != null ) {
                userDto.userRole( user.getUserRole().name() );
            }
        }
        userDto.token( token );

        return userDto.build();
    }

    @Override
    public UserDto toUserDto(ApplicationUser user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.login( user.getLogin() );
        if ( user.getUserRole() != null ) {
            userDto.userRole( user.getUserRole().name() );
        }

        return userDto.build();
    }
}
