package com.pedrycz.tobebought.model.shoppingList;

import com.pedrycz.tobebought.entities.Item;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShoppingListDataDTO {

    private long Id;
    private String name;
    private List<Item> items;
}
