package com.pedrycz.tobebought.model.user;

import com.pedrycz.tobebought.entities.ShoppingList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {

    private String username;
    private String email;
    private List<ShoppingList> shoppingLists;
}
