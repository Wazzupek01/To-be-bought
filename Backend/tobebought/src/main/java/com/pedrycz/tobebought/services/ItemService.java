package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.model.item.ItemDataDTO;

import java.util.List;

public interface ItemService {
    ItemDataDTO getItem(Long id, Long shoppingListId);
    ItemDataDTO saveItem(Item item, Long userId, Long shoppingListId);
    List<ItemDataDTO> getItems(Long shoppingListId);
    ItemDataDTO updateItem(Long id, Long shoppingListId, String name, Float quantity, String unit);

    ItemDataDTO changeItemState(Long id, Long shoppingListId);

    void deleteItem(Long id, Long shoppingListId);

}
