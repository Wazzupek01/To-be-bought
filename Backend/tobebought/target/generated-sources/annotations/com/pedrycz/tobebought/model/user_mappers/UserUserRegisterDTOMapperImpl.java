package com.pedrycz.tobebought.model.user_mappers;

import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.model.user.mappers.UserUserRegisterDTOMapper;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-17T11:19:19+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (N/A)"
)
public class UserUserRegisterDTOMapperImpl implements UserUserRegisterDTOMapper {

    @Override
    public User userRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        if ( userRegisterDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRegisterDTO.getUsername() );
        user.setPassword( userRegisterDTO.getPassword() );
        user.setEmail( userRegisterDTO.getEmail() );

        return user;
    }
}
