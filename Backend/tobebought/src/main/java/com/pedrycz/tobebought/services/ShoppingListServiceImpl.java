package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.item.ItemDataDTO;
import com.pedrycz.tobebought.model.item.ItemItemDataDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.interfaces.ShoppingListService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingListDataDTO getShoppingList(Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        return ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList);
    }

    @Override
    public ShoppingListDataDTO saveShoppingList(ShoppingList shoppingList, Long userId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId));
        shoppingList.setUser(user);
        shoppingListRepository.save(shoppingList);
        return ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList);
    }

    @Override
    public List<ShoppingListDataDTO> getShoppingLists(Long userId) {
        List<ShoppingList> list = shoppingListRepository.findShoppingListsByUserId(userId);
        return ShoppingListDTOMapper.listOfShoppingListToDataDTO(list);
    }

    @Override
    public void deleteShoppingList(Long id, Long userId) {
        shoppingListRepository.deleteShoppingListByIdAndUserId(id, userId);
    }

    @Override
    public List<ItemDataDTO> getListItems(Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        return ItemItemDataDTOMapper.itemListToItemDtoList(shoppingList.getItems());
    }

    @Override
    public ShoppingListDataDTO updateShoppingList(String name, Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        shoppingList.setName(name);
        shoppingListRepository.save(shoppingList);
        return ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingList);
    }

    public static ShoppingList unwrapShoppingList(Optional<ShoppingList> entity, Long id){
        if(entity.isPresent()) return entity.get();
        throw new EntityNotFoundException("Shopping list of id = " + id + " doesn't exist");
    }
}
