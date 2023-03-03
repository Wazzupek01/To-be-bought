package com.pedrycz.tobebought.model.shoppingList;

import com.pedrycz.tobebought.entities.ShoppingList;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShoppingListDTOMapper {

    @Named("shoppingListToShoppingListDataDTO")
    static ShoppingListDataDTO shoppingListToShoppingListDataDTO(ShoppingList shoppingList){
        return new ShoppingListDataDTO(shoppingList.getId(), shoppingList.getName(), shoppingList.getItems());
    }

    @Named("listOfShoppingListToDataDTO")
    static ArrayList<ShoppingListDataDTO> listOfShoppingListToDataDTO(List<ShoppingList> shoppingLists){
        ArrayList<ShoppingListDataDTO> result = new ArrayList<>();
        for(ShoppingList s: shoppingLists){
            result.add(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(s));
        }
        return result;
    }
}
