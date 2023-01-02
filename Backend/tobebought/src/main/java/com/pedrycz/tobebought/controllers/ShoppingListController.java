package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.services.ShoppingListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/shoppingList")
public class ShoppingListController {
    ShoppingListService shoppingListService;

    @GetMapping("/{id}/user/{userId}")
    ResponseEntity<ShoppingList> getShoppingList(@PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<>(shoppingListService.getShoppingList(id, userId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<List<ShoppingList>> getShoppingLists(@PathVariable Long id) {
        return new ResponseEntity<>(shoppingListService.getShoppingLists(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/user/{userId}/all")
    ResponseEntity<List<Item>> getListItems(@PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<>(shoppingListService.getListItems(id, userId), HttpStatus.OK);
    }

    // TODO: ADDS LISTS FOR NON EXISTING USERS
    @PostMapping("/user/{id}")
    ResponseEntity<ShoppingList> addShoppingList(@Valid @RequestBody ShoppingList shoppingList, @PathVariable Long id) {
        return new ResponseEntity<>(shoppingListService.saveShoppingList(shoppingList, id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/user/{userId}")
    ResponseEntity<ShoppingList> updateShoppingList(@Valid @RequestBody ShoppingList shoppingList, @PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<>(shoppingListService.updateShoppingList(shoppingList.getName(), id, userId), HttpStatus.OK);
    }

    // TODO: CANNOT DELETE ENTITY
    @DeleteMapping("/{id}/user/{userId}")
    ResponseEntity<HttpStatus> deleteShoppingList(@PathVariable Long id, @PathVariable Long userId){
        shoppingListService.deleteShoppingList(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

