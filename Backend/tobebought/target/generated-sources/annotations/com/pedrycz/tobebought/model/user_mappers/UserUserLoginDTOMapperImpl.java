package com.pedrycz.tobebought.model.user_mappers;

import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-17T11:19:20+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (N/A)"
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
