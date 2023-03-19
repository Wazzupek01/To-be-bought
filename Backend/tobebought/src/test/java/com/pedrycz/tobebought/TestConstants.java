package com.pedrycz.tobebought;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class TestConstants {

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String PASSWORD = encoder.encode("Password!123");

    public static final List<User> TEST_USERS = List.of(
            new User(1L, "User1", PASSWORD, "wp@wp.pl", List.of()),
            new User(2L, "User2", PASSWORD, "wp2@wp.pl", List.of()),
            new User(3L, "User3", PASSWORD, "wp3@wp.pl", List.of()),
            new User(4L, "User4", PASSWORD, "wp4@wp.pl", List.of())
    );

    public static final List<ShoppingList> TEST_SHOPPING_LISTS = List.of(
            new ShoppingList(1L, "List 1", List.of(), TEST_USERS.get(0)),
            new ShoppingList(2L, "List 2", List.of(), TEST_USERS.get(0)),
            new ShoppingList(3L, "List 3", List.of(), TEST_USERS.get(0)),
            new ShoppingList(4L, "List 4", List.of(), TEST_USERS.get(1)),
            new ShoppingList(5L, "List 5", List.of(), TEST_USERS.get(2)),
            new ShoppingList(6L, "List 6", List.of(), TEST_USERS.get(2)),
            new ShoppingList(7L, "List 7", List.of(), TEST_USERS.get(3))
    );
}
