package com.pedrycz.tobebought.model.user.mappers;

import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-09T13:16:54+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Eclipse Adoptium)"
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
