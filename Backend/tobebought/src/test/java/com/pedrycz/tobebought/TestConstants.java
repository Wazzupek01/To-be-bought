package com.pedrycz.tobebought;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

public class TestConstants {

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String PASSWORD = encoder.encode("Password!123");

    public static final List<User> TEST_USERS = List.of(
            new User(UUID.fromString("00000000-0000-0000-0000-000000000001"), "User1", PASSWORD, "wp@wp.pl", List.of()),
            new User(UUID.fromString("00000000-0000-0000-0000-000000000002"), "User2", PASSWORD, "wp2@wp.pl", List.of()),
            new User(UUID.fromString("00000000-0000-0000-0000-000000000003"), "User3", PASSWORD, "wp3@wp.pl", List.of()),
            new User(UUID.fromString("00000000-0000-0000-0000-000000000004"), "User4", PASSWORD, "wp4@wp.pl", List.of())
    );

    public static final List<ShoppingList> TEST_SHOPPING_LISTS = List.of(
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000001"), "List 1", List.of(), TEST_USERS.get(0)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000002"), "List 2", List.of(), TEST_USERS.get(0)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000003"), "List 3", List.of(), TEST_USERS.get(0)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000004"), "List 4", List.of(), TEST_USERS.get(1)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000005"), "List 5", List.of(), TEST_USERS.get(2)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000006"), "List 6", List.of(), TEST_USERS.get(2)),
            new ShoppingList(UUID.fromString("00000000-0000-0000-0000-000000000007"), "List 7", List.of(), TEST_USERS.get(3))
    );
}
