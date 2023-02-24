package com.pedrycz.tobebought.services.interfaces;

import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;

import java.util.List;

public interface UserService {

    UserLoginDTO loginUser(String username);
    UserDataDTO getUser(Long id);
    UserDataDTO getUser(String username);
    UserDataDTO saveUser(UserRegisterDTO user);
    UserDataDTO updateUser(Long id, String username, String password, String email);
    void deleteUser(Long id);
    List<ShoppingListDataDTO> getUsersLists(Long id);
}
