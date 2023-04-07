package com.pedrycz.tobebought.controllers;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.services.UserServiceImpl;
import com.pedrycz.tobebought.services.interfaces.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/shoppingList")
@SecurityRequirement(name = "Bearer authentication")
@Tag(name = "Shopping List", description = "Contains all operations that can be done on Shopping Lists elements." +
        "You can manage your own shopping lists, as well as get list of items that they contain.")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/{id}")
    @Operation(summary = "Get shopping list", description = "Returns shopping list of given id if it's owned by active user")
    @ApiResponse(responseCode = "200", description = "Shopping list found", content = @Content(schema = @Schema(implementation = ShoppingListDataDTO.class), mediaType="application/json"))
    ResponseEntity<ShoppingListDataDTO> getShoppingList(@PathVariable UUID id, @CookieValue("jwt-token") String token) {
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(shoppingListService.getShoppingList(id, userId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get shopping lists", description = "Returns all shopping lists for a user")
    @ApiResponse(responseCode = "200", description = "Shopping lists found", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ShoppingListDataDTO.class))))
    ResponseEntity<List<ShoppingListDataDTO>> getShoppingLists(@CookieValue("jwt-token") String token) {
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(shoppingListService.getShoppingLists(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/all")
    @Operation(summary = "Get items from shopping list", description = "Returns list of items that shopping list contains")
    @ApiResponse(responseCode = "200", description = "Items found", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ItemDataDTO.class))))
    ResponseEntity<List<ItemDataDTO>> getListItems(@PathVariable UUID id, @CookieValue("jwt-token") String token) {
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(shoppingListService.getListItems(id, userId), HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Add shopping list", description = "Adds shopping list for active user")
    @ApiResponse(responseCode = "201", description = "Shopping list created", content = @Content(schema = @Schema(implementation = ShoppingListDataDTO.class), mediaType="application/json"))
    ResponseEntity<ShoppingListDataDTO> addShoppingList(@Valid @RequestBody ShoppingList shoppingList,
                                                        @CookieValue("jwt-token") String token) {
        UUID id = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(shoppingListService.saveShoppingList(shoppingList, id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update shopping list", description = "Updates name of shopping list")
    @ApiResponse(responseCode = "200", description = "Shopping list updated", content = @Content(schema = @Schema(implementation = ShoppingListDataDTO.class), mediaType="application/json"))
    ResponseEntity<ShoppingListDataDTO> updateShoppingList(@Valid @RequestBody ShoppingList shoppingList,
                                                           @PathVariable UUID id,
                                                           @CookieValue("jwt-token") String token) {
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        return new ResponseEntity<>(shoppingListService.updateShoppingList(shoppingList.getName(),
                id,
                userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete shopping list", description = "Deletes shopping list and all of its items")
    @ApiResponse(responseCode = "204", description = "Shopping list deleted", content = @Content)
    ResponseEntity<HttpStatus> deleteShoppingList(@PathVariable UUID id, @CookieValue("jwt-token") String token){
        UUID userId = UserServiceImpl.getUserIdFromJWT(token);
        shoppingListService.deleteShoppingList(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

