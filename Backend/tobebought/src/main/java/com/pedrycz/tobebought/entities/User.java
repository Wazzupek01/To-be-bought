package com.pedrycz.tobebought.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedrycz.tobebought.validation.Password;
import com.pedrycz.tobebought.validation.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Username
    private String username;

    @Password
    private String password;

    @NotBlank(message = "Email can't be blank")
    @Email
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShoppingList> shoppingLists;
}
