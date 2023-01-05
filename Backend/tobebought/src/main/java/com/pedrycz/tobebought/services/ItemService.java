package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;

import java.util.List;

public interface ItemService {
// TODO: RECEIVE DTO AND RETURN DTO
    Item getItem(Long id, Long shoppingListId);
    Item saveItem(Item item, Long userId, Long shoppingListId);
    List<Item> getItems(Long shoppingListId);
    Item updateItem(Long id, Long shoppingListId, String name, Float quantity, String unit);

    Item changeItemState(Long id, Long shoppingListId);

    void deleteItem(Long id, Long shoppingListId);

}
