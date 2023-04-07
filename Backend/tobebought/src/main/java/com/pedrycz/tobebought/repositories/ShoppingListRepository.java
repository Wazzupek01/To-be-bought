package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.ShoppingList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {

    Optional<ShoppingList> findShoppingListByIdAndUserId(UUID id, UUID userId);
    List<ShoppingList> findShoppingListsByUserId(UUID userId);

    @Transactional
    void deleteShoppingListByIdAndUserId(UUID id, UUID userId);
}
