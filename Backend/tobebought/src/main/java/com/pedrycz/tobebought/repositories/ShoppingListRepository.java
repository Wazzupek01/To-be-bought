package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.ShoppingList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    Optional<ShoppingList> findShoppingListByIdAndUserId(Long id, Long userId);
    List<ShoppingList> findShoppingListsByUserId(Long userId);
    @Transactional
    void deleteShoppingListByIdAndUserId(Long id, Long userId);
}
