package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.ShoppingList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    Optional<ShoppingList> findShoppingListByIdAndUserId(Long id, Long userId);
    List<ShoppingList> findShoppingListsByUserId(Long userId);
    void deleteShoppingListByIdAndUserId(Long id, Long userId);
}
