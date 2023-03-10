package com.pedrycz.tobebought.unit;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.exceptions.ShoppingListNotOwnedException;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.item.ItemItemDataDTOMapperImpl;
import com.pedrycz.tobebought.repositories.ItemRepository;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.ItemServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @InjectMocks
    private ItemItemDataDTOMapperImpl itemDataDTOMapper;

    @InjectMocks
    private BCryptPasswordEncoder passwdEncoder;

    @Test
    public void getItemTest() {
        // Given
        Item item = new Item("cucumber", 1.5F, "kilogram");
        item.setId(1L);
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        item.setShoppingList(shoppingList);

        // When
        when(itemRepository.findByIdAndShoppingListId(item.getId(), shoppingList.getId())).thenReturn(Optional.of(item));

        // Then
        ItemDataDTO result = itemService.getItem(item.getId(), item.getShoppingList().getId());
        assertEquals(itemDataDTOMapper.itemToItemDataDTO(item), result);
        assertThrows(EntityNotFoundException.class, () -> itemService.getItem(item.getId(), 2L));
        assertThrows(EntityNotFoundException.class, () -> itemService.getItem(2L, 1L));
    }

    @Test
    public void saveItemTest() {
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        ShoppingList notOwned = new ShoppingList("List not owned");
        notOwned.setId(2L);

        User user = new User(1L, "User1",
                passwdEncoder.encode("Password!123"), "wp@wp.pl", List.of(shoppingList));
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);

        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(shoppingListRepository.findById(shoppingList.getId())).thenReturn(Optional.of(shoppingList));
        when(shoppingListRepository.findById(notOwned.getId())).thenReturn(Optional.of(notOwned));
        // Then

        ItemDataDTO result = itemService.saveItem(item, user.getId(), shoppingList.getId());
        assertEquals(itemDataDTOMapper.itemToItemDataDTO(item), result);

        assertThrows(ShoppingListNotOwnedException.class, () -> itemService.saveItem(item, user.getId(), 2L));
        assertThrows(EntityNotFoundException.class, () -> itemService.saveItem(item, 2L, 1L));
        assertThrows(EntityNotFoundException.class, () -> itemService.saveItem(item, 1L, 3L));

        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void getItemsTest(){
        // Given
        ShoppingList shoppingList1 = new ShoppingList("List 1");
        shoppingList1.setId(1L);
        ShoppingList shoppingList2 = new ShoppingList("List 1");
        shoppingList2.setId(2L);
        List<Item> items = List.of(
                new Item("item", 2F, "kilogram"),
                new Item("item 2", 1F, "unit"),
                new Item("item 3", 4F, "gram"));
        shoppingList1.setItems(List.of(items.get(0), items.get(1)));
        shoppingList2.setItems(List.of(items.get(2)));

        List<ItemDataDTO> itemsDTO = new ArrayList<>();
        for(Item i: items) itemsDTO.add(itemDataDTOMapper.itemToItemDataDTO(i));

        // When
        when(itemRepository.findByShoppingListId(shoppingList1.getId())).thenReturn(shoppingList1.getItems());
        when(itemRepository.findByShoppingListId(shoppingList2.getId())).thenReturn(shoppingList2.getItems());

        // Then

        List<ItemDataDTO> result1 = itemService.getItems(shoppingList1.getId());
        List<ItemDataDTO> result2 = itemService.getItems(shoppingList2.getId());

        assertEquals(List.of(itemsDTO.get(0), itemsDTO.get(1)), result1);
        assertEquals(List.of(itemsDTO.get(2)), result2);
        assertEquals(0, itemService.getItems(3L).size());
    }

    @Test
    public void updateItemTest(){
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);
        Item updatedItem = new Item("coconut", 1F, "kilogram");
        updatedItem.setId(1L);
        updatedItem.setShoppingList(shoppingList);
        ItemDataDTO updatedItemDataDTO = itemDataDTOMapper.itemToItemDataDTO(updatedItem);

        // When
        when(itemRepository.findByIdAndShoppingListId(item.getId(), item.getShoppingList().getId())).thenReturn(Optional.of(item));

        // Then
        ItemDataDTO result = itemService.updateItem(
                item.getId(),
                item.getShoppingList().getId(),
                "coconut",
                1F,
                item.getUnit());
        item.setName("coconut");
        item.setQuantity(1F);
        verify(itemRepository, times(1)).save(item);

        assertEquals(updatedItemDataDTO, result);
        assertThrows(EntityNotFoundException.class, () -> itemService.updateItem(
                item.getId(),
                2L,
                "coconut",
                1F,
                item.getUnit()));
        assertThrows(EntityNotFoundException.class, () -> itemService.updateItem(
                2L,
                item.getShoppingList().getId(),
                "coconut",
                1F,
                item.getUnit()));
    }

    @Test
    public void changeItemStateTest() {
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);

        // When
        when(itemRepository.findByIdAndShoppingListId(item.getId(), item.getShoppingList().getId())).thenReturn(Optional.of(item));

        // Then
        itemService.changeItemState(item.getId(), item.getShoppingList().getId());
        verify(itemRepository, times(1)).save(item);
        assertThrows(EntityNotFoundException.class, () -> itemService.changeItemState(item.getId(), 2L));
        assertThrows(EntityNotFoundException.class, () -> itemService.changeItemState(2L, item.getShoppingList().getId()));
    }

    @Test
    public void deleteItemTest() {
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);

        // When
        itemService.deleteItem(item.getId(), item.getShoppingList().getId());

        // Then
        verify(itemRepository, times(1)).deleteByIdAndShoppingListId(item.getId(), item.getShoppingList().getId());
    }
}
