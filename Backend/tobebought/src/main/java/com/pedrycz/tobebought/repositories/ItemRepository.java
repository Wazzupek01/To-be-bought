package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findByIdAndShoppingListId(UUID id, UUID shoppingListId);
    List<Item> findByShoppingListId(UUID shoppingListId);

    @Transactional
    void deleteByIdAndShoppingListId(UUID id, UUID shoppingListId);
}

