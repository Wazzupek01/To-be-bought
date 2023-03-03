package com.pedrycz.tobebought;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.item.ItemItemDataDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.ShoppingListServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShoppingListServiceImpl shoppingListService;

    @InjectMocks
    private BCryptPasswordEncoder encoder;

    @Test
    public void getShoppingListTest(){
        // Given
        ShoppingList shoppingList = new ShoppingList("list 1");
        shoppingList.setId(1L);
        shoppingList.setItems(List.of(new Item("cucumber", 1.5F, "kilogram")));
        shoppingList.getItems().get(0).setShoppingList(shoppingList);
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl", List.of(shoppingList));
        shoppingList.setUser(user);

        // When
        when(shoppingListRepository.findShoppingListByIdAndUserId(shoppingList.getId(), shoppingList.getUser().getId()))
                .thenReturn(Optional.of(shoppingList));

        // Then
        ShoppingListDataDTO result = shoppingListService.getShoppingList(shoppingList.getId(), shoppingList.getUser().getId());
        assertEquals(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList), result);
        assertThrows(EntityNotFoundException.class, () -> shoppingListService.getShoppingList(shoppingList.getId(), 2L));
        assertThrows(EntityNotFoundException.class, () -> shoppingListService.getShoppingList(2L, shoppingList.getUser().getId()));
    }

    @Test
    public void saveShoppingList() {
        // Given
        ShoppingList shoppingList = new ShoppingList("list 1");
        shoppingList.setId(1L);
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl", List.of());

        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Then
        ShoppingListDataDTO result = shoppingListService.saveShoppingList(shoppingList, user.getId());
        assertEquals(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList),result);
        verify(shoppingListRepository, times(1)).save(shoppingList);
    }

    @Test
    public void getShoppingListsTest() {
        // Given
        ShoppingList shoppingList = new ShoppingList("list 1");
        shoppingList.setId(1L);
        ShoppingList shoppingList2 = new ShoppingList("list 2");
        shoppingList.setId(10L);
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl",
                List.of(shoppingList, shoppingList2));

        // When
        when(shoppingListRepository.findShoppingListsByUserId(user.getId())).thenReturn(user.getShoppingLists());

        // Then
        List<ShoppingListDataDTO> result = shoppingListService.getShoppingLists(user.getId());
        assertEquals(ShoppingListDTOMapper.listOfShoppingListToDataDTO(List.of(shoppingList, shoppingList2)), result);
    }


    @Test
    public void deleteShoppingList(){
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);
        shoppingList.setItems(List.of(item));
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl",
                List.of(shoppingList));

        // Then
        shoppingListService.deleteShoppingList(shoppingList.getId(), user.getId());
        verify(shoppingListRepository, times(1)).deleteShoppingListByIdAndUserId(shoppingList.getId(), user.getId());
    }

    @Test
    public void getListItemsTest(){
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);
        Item item2 = new Item("melon", 1F, "piece");
        item.setId(23L);
        item.setShoppingList(shoppingList);
        Item item3 = new Item("crisps", 1F, "pack");
        item.setId(100L);
        item.setShoppingList(shoppingList);
        shoppingList.setItems(List.of(item, item2, item3));
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl",
                List.of(shoppingList));

        // When
        when(shoppingListRepository.findShoppingListByIdAndUserId(shoppingList.getId(), user.getId()))
                .thenReturn(Optional.of(user.getShoppingLists().get(0)));

        // Then
        List<ItemDataDTO> result = shoppingListService.getListItems(shoppingList.getId(), user.getId());
        assertEquals(ItemItemDataDTOMapper.itemListToItemDtoList(shoppingList.getItems()), result);
    }

    @Test
    public void updateShoppingListTest() {
        // Given
        ShoppingList shoppingList = new ShoppingList("List 1");
        shoppingList.setId(1L);
        Item item = new Item("cucumber", 2F, "kilogram");
        item.setId(1L);
        item.setShoppingList(shoppingList);
        shoppingList.setItems(List.of(item));
        User user = new User(1L, "User1", encoder.encode("Password!123"), "wp@wp.pl",
                List.of(shoppingList));

        // When
        when(shoppingListRepository.findShoppingListByIdAndUserId(shoppingList.getId(), user.getId()))
                .thenReturn(Optional.of(shoppingList));

        // Then
        ShoppingListDataDTO result = shoppingListService.updateShoppingList("LIST #1", shoppingList.getId(), user.getId());
        shoppingList.setName("LIST #1");
        assertEquals(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList), result);
        verify(shoppingListRepository, times(1)).save(shoppingList);
    }
}