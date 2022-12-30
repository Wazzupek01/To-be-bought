package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByIdAndShoppingListId(Long id, Long shoppingListId);
    List<Item> findByShoppingListId( Long shoppingListId);
    void deleteByIdAndShoppingListId(Long id, Long shoppingListId);
}

