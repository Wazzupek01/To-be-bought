package com.pedrycz.tobebought.services.interfaces;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {

    ShoppingListDataDTO getShoppingList(UUID id, UUID userId);
    List<ShoppingListDataDTO> getShoppingLists(UUID userId);
    List<ItemDataDTO> getListItems(UUID id, UUID userId);
    ShoppingListDataDTO saveShoppingList(ShoppingList shoppingList, UUID userId);
    ShoppingListDataDTO updateShoppingList(String name, UUID id, UUID userId);
    void deleteShoppingList(UUID id, UUID userId);
}