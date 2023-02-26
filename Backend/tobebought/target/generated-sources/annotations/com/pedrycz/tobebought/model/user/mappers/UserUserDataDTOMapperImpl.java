package com.pedrycz.tobebought.model.user.mappers;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-26T14:39:15+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (N/A)"
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
