package com.pedrycz.tobebought.services.interfaces;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.model.item.ItemDataDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    ItemDataDTO getItem(UUID id, UUID shoppingListId);
    ItemDataDTO saveItem(Item item, UUID userId, UUID shoppingListId);
    List<ItemDataDTO> getItems(UUID shoppingListId);
    ItemDataDTO updateItem(UUID id, UUID shoppingListId, String name, Float quantity, String unit);
    ItemDataDTO changeItemState(UUID id, UUID shoppingListId);
    void deleteItem(UUID id, UUID shoppingListId);

}
