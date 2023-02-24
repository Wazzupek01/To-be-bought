package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdAndShoppingListId(Long id, Long shoppingListId);
    List<Item> findByShoppingListId(Long shoppingListId);

    @Transactional
    void deleteByIdAndShoppingListId(Long id, Long shoppingListId);
}

