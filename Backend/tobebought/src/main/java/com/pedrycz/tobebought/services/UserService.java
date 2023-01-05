package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;

import java.util.List;

public interface UserService {

    UserLoginDTO loginUser(String username);
    UserDataDTO getUser(Long id);
    UserDataDTO getUser(String username);
    //TODO: RETURN VALUE IS NEVER USED
    UserDataDTO saveUser(UserRegisterDTO user);
    UserDataDTO updateUser(Long id, String username, String password, String email);
    void deleteUser(Long id);
    List<ShoppingList> getUsersLists(Long id);
}
