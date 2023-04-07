package com.pedrycz.tobebought.model.shoppingList;

import com.pedrycz.tobebought.entities.Item;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShoppingListDataDTO {

    private UUID Id;
    private String name;
    private List<Item> items;
}
