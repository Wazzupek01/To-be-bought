package com.pedrycz.tobebought.model.user_mappers;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.mappers.UserUserDataDTOMapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-17T11:19:20+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (N/A)"
)
public class UserUserDataDTOMapperImpl implements UserUserDataDTOMapper {

    @Override
    public UserDataDTO userToUserDataDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDataDTO userDataDTO = new UserDataDTO();

        userDataDTO.setUsername( user.getUsername() );
        userDataDTO.setEmail( user.getEmail() );
        List<ShoppingList> list = user.getShoppingLists();
        if ( list != null ) {
            userDataDTO.setShoppingLists( new ArrayList<ShoppingList>( list ) );
        }

        return userDataDTO;
    }
}
