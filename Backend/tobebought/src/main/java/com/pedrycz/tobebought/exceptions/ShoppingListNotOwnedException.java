package com.pedrycz.tobebought.exceptions;

import java.util.UUID;

public class ShoppingListNotOwnedException extends RuntimeException{

    public ShoppingListNotOwnedException(UUID userId, UUID shoppingListId){
        super("User with id = " + userId.toString() + " doesn't own shopping list of id = " + shoppingListId.toString());
    }
}
