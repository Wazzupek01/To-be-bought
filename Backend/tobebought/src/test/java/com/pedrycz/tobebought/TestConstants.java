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
            new ShoppingList("List 1"),
            new ShoppingList("List 2"),
            new ShoppingList("List 3"),
            new ShoppingList("List 4"),
            new ShoppingList("List 5"),
            new ShoppingList("List 6"),
            new ShoppingList("List 7")
    );
}
