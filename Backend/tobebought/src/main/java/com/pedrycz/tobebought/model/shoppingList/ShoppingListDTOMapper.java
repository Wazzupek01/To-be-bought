package com.pedrycz.tobebought.model.shoppingList;

import com.pedrycz.tobebought.entities.ShoppingList;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface ShoppingListDTOMapper {
    @Named("shoppingListToShoppingListDataDTO")
    static ShoppingListDataDTO shoppingListToShoppingListDataDTO(ShoppingList shoppingList){
        return new ShoppingListDataDTO(shoppingList.getId(), shoppingList.getName(), shoppingList.getItems());
    }
}
