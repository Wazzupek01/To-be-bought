package com.pedrycz.tobebought.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username can't be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "Password can't be blank")
    @NonNull
    @Column(nullable = false)
    private String password;
    private String email;
//    private List<ShoppingList> shoppingLists;
}
