package com.pedrycz.tobebought.model.user.mappers;

import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-07T17:41:41+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (N/A)"
)
public class UserUserLoginDTOMapperImpl implements UserUserLoginDTOMapper {

    @Override
    public UserLoginDTO userToUserLoginDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();

        userLoginDTO.setId( user.getId() );
        userLoginDTO.setUsername( user.getUsername() );
        userLoginDTO.setPassword( user.getPassword() );

        return userLoginDTO;
    }
}
