package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.exceptions.ShoppingListNotOwnedException;
import com.pedrycz.tobebought.repositories.ItemRepository;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private ShoppingListRepository shoppingListRepository;

    @Override
    public Item getItem(Long id, Long shoppingListId) {
        return unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id);
    }

    @Override
    public Item saveItem(Item item, Long userId, Long shoppingListId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId));
        ShoppingList shoppingList = ShoppingListServiceImpl
                .unwrapShoppingList(shoppingListRepository.findById(shoppingListId), shoppingListId);
        if(!user.getShoppingLists().contains(shoppingList))
            throw new ShoppingListNotOwnedException(userId, shoppingListId);
        item.setShoppingList(shoppingList);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItems(Long shoppingListId) {
        return itemRepository.findByShoppingListId(shoppingListId);
    }

    @Override
    public Item updateItem(Long id, Long shoppingListId, String name, Float quantity, String unit) {
        Item item = unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id);
        item.setName(name);
        item.setQuantity(quantity);
        item.setUnit(unit);
        return itemRepository.save(item);
    }

    @Override
    public Item changeItemState(Long id, Long shoppingListId) {
        Item item = unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id);
        item.setChecked(!item.isChecked());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id, Long shoppingListId) {
        itemRepository.deleteByIdAndShoppingListId(id, shoppingListId);
    }

    static Item unwrapItem(Optional<Item> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("Item of id = " + id + " doesn't exist");
    }
}
