package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.services.interfaces.ItemService;
import com.pedrycz.tobebought.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/shoppingList/{shoppingListId}")
    public ResponseEntity<ItemDataDTO> addItem(@Valid @RequestBody Item item,
                                               @PathVariable Long shoppingListId,
                                               @CookieValue("jwt-token") String token) {
        Long userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(itemService.saveItem(item, userId, shoppingListId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<ItemDataDTO> updateItem(@Valid @RequestBody Item item,
                                                  @PathVariable Long id,
                                                  @PathVariable Long shoppingListId) {
        return new ResponseEntity<>(itemService.updateItem(id,
                shoppingListId,
                item.getName(),
                item.getQuantity(),
                item.getUnit()), HttpStatus.OK);
    }

    @PutMapping("/check/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<ItemDataDTO> changeItemState(@PathVariable Long id, @PathVariable Long shoppingListId) {
        return new ResponseEntity<>(itemService.changeItemState(id, shoppingListId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/shoppingList/{shoppingListId}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id, @PathVariable Long shoppingListId) {
        itemService.deleteItem(id, shoppingListId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
