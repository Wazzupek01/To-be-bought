package com.pedrycz.tobebought.services.interfaces;

import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserLoginDTO loginUser(String username);
    UserDataDTO getUser(UUID id);
    UserDataDTO getUser(String username);
    UserDataDTO saveUser(UserRegisterDTO user);
    UserDataDTO updateUser(UUID id, String username, String password, String email);
    void deleteUser(UUID id);
    List<ShoppingListDataDTO> getUsersLists(UUID id);
}
