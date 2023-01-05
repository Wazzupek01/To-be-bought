package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.services.ItemService;
import com.pedrycz.tobebought.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
// DELETE: GET ITEM, GET ITEMS,
    ItemService itemService;

//    @GetMapping("/{id}/user/{userId}/shoppingList/{shoppingListId}")
//    public ResponseEntity<Item> getItem(@PathVariable Long id, @CookieValue("jwt-token") String token, @PathVariable Long shoppingListId){
//        return new ResponseEntity<>(itemService.getItem(id, shoppingListId), HttpStatus.OK);
//    }

//    @GetMapping("/shoppingList/{shoppingListId}")
//    public ResponseEntity<List<Item>> getItemsFromShoppingList(@PathVariable Long shoppingListId){
//
//        return new ResponseEntity<>(itemService.getItems(shoppingListId), HttpStatus.OK);
//    }

    @PostMapping("/shoppingList/{shoppingListId}")
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item, @PathVariable Long shoppingListId, @CookieValue("jwt-token") String token){
        Long userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(itemService.saveItem(item, userId, shoppingListId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item, @PathVariable Long id, @PathVariable Long shoppingListId){
        return new ResponseEntity<>(itemService.updateItem(id, shoppingListId, item.getName(), item.getQuantity(), item.getUnit()), HttpStatus.OK);
    }

    @PutMapping("/check/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<Item> changeItemState(@PathVariable Long id, @PathVariable Long shoppingListId){
        return new ResponseEntity<>(itemService.changeItemState(id, shoppingListId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id, @PathVariable Long shoppingListId){
        itemService.deleteItem(id, shoppingListId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
