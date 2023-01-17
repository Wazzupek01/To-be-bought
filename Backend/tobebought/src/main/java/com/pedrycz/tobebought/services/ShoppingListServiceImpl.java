package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.Item;
import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.repositories.ShoppingListRepository;
import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ShoppingListServiceImpl implements ShoppingListService{

    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private UserRepository userRepository;
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
        List<ShoppingListDataDTO> listDto = new ArrayList<ShoppingListDataDTO>();
        for(ShoppingList s: list){
            listDto.add(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(s));
        }
        return listDto;
    }

    @Override
    public void deleteShoppingList(Long id, Long userId) {
        shoppingListRepository.deleteShoppingListByIdAndUserId(id, userId);
    }

    @Override
    public List<Item> getListItems(Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        return shoppingList.getItems();
    }

    @Override
    public ShoppingListDataDTO updateShoppingList(String name, Long id, Long userId) {
        ShoppingList shoppingList = unwrapShoppingList(shoppingListRepository.findShoppingListByIdAndUserId(id, userId), id);
        shoppingList.setName(name);
        return ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(shoppingListRepository.save(shoppingList));
    }

    static ShoppingList unwrapShoppingList(Optional<ShoppingList> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("Shopping list of id = " + id + " doesn't exist");
    }
}
