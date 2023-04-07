package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.services.interfaces.UserService;
import com.pedrycz.tobebought.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "All operations for user account management.")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Findy by id", description = "Finds user by ID in jwt-token")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDataDTO.class), mediaType="application/json"))
    public ResponseEntity<UserDataDTO> findById(@CookieValue(name="jwt-token") String token){
        return new ResponseEntity<>(userService.getUser(UserServiceImpl.getUserIdFromJWT(token)), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Registers user with username, email and password. Username and email must be unique." +
            "Password must contain min. 1 special character, 1 capital letter, 1 lower case letter and 1 number")
    @ApiResponse(responseCode = "201", description = "User registered")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserRegisterDTO user){
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Update user", description = "updates data for active user")
    @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserDataDTO.class), mediaType="application/json"))
    public ResponseEntity<UserDataDTO> updateUser(@Valid @RequestBody UserRegisterDTO user,
                                                  @CookieValue(name="jwt-token") String token){
        UUID id = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(userService.updateUser(id,
                user.getUsername(),
                user.getPassword(),
                user.getEmail()),
                HttpStatus.OK);
    }

    @DeleteMapping("/")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Delete user", description = "Deletes active user")
    @ApiResponse(responseCode = "204", description = "User updated", content = @Content)
    public ResponseEntity<HttpStatus> deleteUser(@CookieValue(name="jwt-token") String token){
        userService.deleteUser(UserServiceImpl.getUserIdFromJWT(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/lists")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Get users lists", description = "Returns all shopping lists for active user")
    @ApiResponse(responseCode = "204", description = "User updated", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ShoppingListDataDTO.class))))
    public ResponseEntity<List<ShoppingListDataDTO>> getUsersLists(@CookieValue(name="jwt-token") String token){
        return new ResponseEntity<>(userService.getUsersLists(UserServiceImpl.getUserIdFromJWT(token)), HttpStatus.OK);
    }
}
