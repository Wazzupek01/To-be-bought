package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.services.UserServiceImpl;
import com.pedrycz.tobebought.services.interfaces.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
@SecurityRequirement(name = "Bearer authentication")
@Tag(name = "Item", description = "Contains all management operations that can be done on Item elements.")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/shoppingList/{shoppingListId}")
    @Operation(summary = "Add item to shopping list", description = "Allows to create and add item to existing shopping list")
    @ApiResponse(responseCode = "201", description = "Item created and added to shopping list", content = @Content(schema = @Schema(implementation = ItemDataDTO.class), mediaType = "application/json"))
    public ResponseEntity<ItemDataDTO> addItem(@Valid @RequestBody Item item,
                                               @PathVariable UUID shoppingListId,
                                               @CookieValue("jwt-token") String token) {
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(itemService.saveItem(item, userId, shoppingListId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/shoppingList/{shoppingListId}")
    @Operation(summary = "Update item", description = "Updates existing item with new name, quantity or unit")
    @ApiResponse(responseCode = "200", description = "Item data updated", content = @Content(schema = @Schema(implementation = ItemDataDTO.class), mediaType = "application/json"))
    public ResponseEntity<ItemDataDTO> updateItem(@Valid @RequestBody Item item,
                                                  @PathVariable UUID id,
                                                  @PathVariable UUID shoppingListId) {
        return new ResponseEntity<>(itemService.updateItem(id,
                shoppingListId,
                item.getName(),
                item.getQuantity(),
                item.getUnit()), HttpStatus.OK);
    }

    @PutMapping("/check/{id}/shoppingList/{shoppingListId}")
    @Operation(summary = "Change state of item", description = "Sets state of item (whether checked as bought or not) to the opposite of actual")
    @ApiResponse(responseCode = "200", description = "Item state changed", content = @Content(schema = @Schema(implementation = ItemDataDTO.class), mediaType = "application/json"))
    public ResponseEntity<ItemDataDTO> changeItemState(@PathVariable UUID id, @PathVariable UUID shoppingListId) {
        return new ResponseEntity<>(itemService.changeItemState(id, shoppingListId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/shoppingList/{shoppingListId}")
    @Operation(summary = "Delete item", description = "Deletes item from shopping list and db")
    @ApiResponse(responseCode = "204", description = "Item deleted", content = @Content)
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable UUID id, @PathVariable UUID shoppingListId) {
        itemService.deleteItem(id, shoppingListId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
