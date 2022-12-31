package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    @Override
    public User getUser(Long id) {
        return unwrapUser(userRepository.findById(id), id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String username, String password, String email) {
        User user = unwrapUser(userRepository.findById(id), id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<ShoppingList> getUsersLists(Long id) {
        User user = unwrapUser(userRepository.findById(id),id);
        return user.getShoppingLists();
    }

    static User unwrapUser(Optional<User> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("User of id = " + id + " doesn't exist");

    }
}
