package com.pedrycz.tobebought.exceptions;

public class ShoppingListNotOwnedException extends RuntimeException{

    public ShoppingListNotOwnedException(Long userId, Long shoppingListId){
        super("User with id = " + userId + " doesn't own shopping list of id = " + shoppingListId);
    }
}
