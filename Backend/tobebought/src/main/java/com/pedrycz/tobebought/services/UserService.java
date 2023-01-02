package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    //TODO: RETURN VALUE IS NEVER USED
    User saveUser(User user);
    User updateUser(Long id, String username, String password, String email);
    void deleteUser(Long id);
    List<ShoppingList> getUsersLists(Long id);
}
