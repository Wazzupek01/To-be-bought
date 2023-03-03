package com.pedrycz.tobebought.services.interfaces;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;

import java.util.List;

public interface ShoppingListService {

    ShoppingListDataDTO getShoppingList(Long id, Long userId);
    List<ShoppingListDataDTO> getShoppingLists(Long userId);
    List<ItemDataDTO> getListItems(Long id, Long userId);
    ShoppingListDataDTO saveShoppingList(ShoppingList shoppingList, Long userId);
    ShoppingListDataDTO updateShoppingList(String name, Long id, Long userId);
    void deleteShoppingList(Long id, Long userId);
}