package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.exceptions.ShoppingListNotOwnedException;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.item.ItemItemDataDTOMapper;
import com.pedrycz.tobebought.repositories.ItemRepository;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.interfaces.ItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final ItemItemDataDTOMapper mapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.mapper = Mappers.getMapper(ItemItemDataDTOMapper.class);
    }


    @Override
    public ItemDataDTO getItem(Long id, Long shoppingListId) {
        return mapper.itemToItemDataDTO(unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id));
    }

    @Override
    public ItemDataDTO saveItem(Item item, Long userId, Long shoppingListId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId));
        ShoppingList shoppingList = ShoppingListServiceImpl
                .unwrapShoppingList(shoppingListRepository.findById(shoppingListId), shoppingListId);
        if(!user.getShoppingLists().contains(shoppingList))
            throw new ShoppingListNotOwnedException(userId, shoppingListId);
        item.setShoppingList(shoppingList);
        return mapper.itemToItemDataDTO(itemRepository.save(item));
    }

    @Override
    public List<ItemDataDTO> getItems(Long shoppingListId) {
        List<Item> list = itemRepository.findByShoppingListId(shoppingListId);
        List<ItemDataDTO> listDTO = new ArrayList<>();
        for(Item i: list){
            listDTO.add(mapper.itemToItemDataDTO(i));
        }
        return listDTO;
    }

    @Override
    public ItemDataDTO updateItem(Long id, Long shoppingListId, String name, Float quantity, String unit) {
        Item item = unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id);
        item.setName(name);
        item.setQuantity(quantity);
        item.setUnit(unit);
        return mapper.itemToItemDataDTO(itemRepository.save(item));
    }

    @Override
    public ItemDataDTO changeItemState(Long id, Long shoppingListId) {
        Item item = unwrapItem(itemRepository.findByIdAndShoppingListId(id, shoppingListId), id);
        item.setChecked(!item.isChecked());
        return mapper.itemToItemDataDTO(itemRepository.save(item));
    }

    @Override
    public void deleteItem(Long id, Long shoppingListId) {
        itemRepository.deleteByIdAndShoppingListId(id, shoppingListId);
    }

    public static Item unwrapItem(Optional<Item> entity, Long id){
        if(entity.isPresent()) return entity.get();
        throw new EntityNotFoundException("Item of id = " + id + " doesn't exist");
    }
}
