package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

// TODO: RECEIVE DTO AND RETURN DTO

@Service
@AllArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService{

    private ShoppingListRepository shoppingListRepository;
    private UserRepository userRepository;

    @Override
    public ShoppingList getShoppingList(Long id, Long userId) {
        return unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
    }

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList, Long userId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId), userId);
        shoppingList.setUser(user);
        return shoppingListRepository.save(shoppingList);
    }

    @Override
    public List<ShoppingList> getShoppingLists(Long userId) {
        return shoppingListRepository.findShoppingListsByUserId(userId);
    }

    @Override
    public void deleteShoppingList(Long id, Long userId) {
        shoppingListRepository.deleteShoppingListByIdAndUserId(id, userId);
    }

    @Override
    public List<Item> getListItems(Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        return shoppingList.getItems();
    }

    @Override
    public ShoppingList updateShoppingList(String name, Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        shoppingList.setName(name);
        return shoppingListRepository.save(shoppingList);
    }

    static ShoppingList unwrapShoppingList(Optional<ShoppingList> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("Shopping list of id = " + id + " doesn't exist");
    }
}
