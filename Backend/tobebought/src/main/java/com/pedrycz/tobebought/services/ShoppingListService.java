package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;

import java.util.List;

public interface ShoppingListService {
    // TODO: RECEIVE DTO AND RETURN DTO
    ShoppingList getShoppingList(Long id, Long userId);
    List<ShoppingList> getShoppingLists(Long userId);
    List<Item> getListItems(Long id, Long userId);
    ShoppingList saveShoppingList(ShoppingList shoppingList, Long userId);
    ShoppingList updateShoppingList(String name, Long id, Long userId);
    void deleteShoppingList(Long id, Long userId);
}