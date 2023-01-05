package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.services.UserService;
import com.pedrycz.tobebought.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserDataDTO> findById(@CookieValue(name="jwt-token") String token){
        return new ResponseEntity<>(userService.getUser(UserServiceImpl.getUserIdFromJWT(token)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserRegisterDTO user){
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<UserDataDTO> updateUser(@Valid @RequestBody UserRegisterDTO user, @CookieValue(name="jwt-token") String token){
        Long id = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(userService.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteUser(@CookieValue(name="jwt-token") String token){
        userService.deleteUser(UserServiceImpl.getUserIdFromJWT(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<ShoppingList>> getUsersLists(@CookieValue(name="jwt-token") String token){
        return new ResponseEntity<>(userService.getUsersLists(UserServiceImpl.getUserIdFromJWT(token)), HttpStatus.OK);
    }
}
