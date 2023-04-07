package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDTOMapper;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.model.user.mappers.UserUserDataDTOMapper;
import com.pedrycz.tobebought.model.user.mappers.UserUserLoginDTOMapper;
import com.pedrycz.tobebought.model.user.mappers.UserUserRegisterDTOMapper;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserUserDataDTOMapper dataDTOMapper;
    private final UserUserRegisterDTOMapper registerDTOMapper;
    private final UserUserLoginDTOMapper loginDTOMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.dataDTOMapper = Mappers.getMapper(UserUserDataDTOMapper.class);
        this.registerDTOMapper = Mappers.getMapper(UserUserRegisterDTOMapper.class);
        this.loginDTOMapper = Mappers.getMapper(UserUserLoginDTOMapper.class);
    }

    @Override
    public UserLoginDTO loginUser(String username) {
        User user = unwrapUser(userRepository.findByUsername(username));
        return loginDTOMapper.userToUserLoginDTO(user);
    }

    @Override
    public UserDataDTO getUser(UUID id) {
        User user = unwrapUser(userRepository.findById(id));
        return dataDTOMapper.userToUserDataDTO(user);
    }

    @Override
    public UserDataDTO getUser(String username) {
        User user = unwrapUser(userRepository.findByUsername(username));
        return dataDTOMapper.userToUserDataDTO(user);
    }

    @Override
    public UserDataDTO saveUser(UserRegisterDTO user) {
        User userRegister = registerDTOMapper.userRegisterDTOToUser(user);
        userRegister.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
        userRepository.save(userRegister);
        return dataDTOMapper.userToUserDataDTO(userRegister);
    }

    @Override
    public UserDataDTO updateUser(UUID id, String username, String password, String email) {
        User user = unwrapUser(userRepository.findById(id));
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
        return dataDTOMapper.userToUserDataDTO(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<ShoppingListDataDTO> getUsersLists(UUID id) {
        User user = unwrapUser(userRepository.findById(id));
        List<ShoppingList> list = user.getShoppingLists();
        if(list == null) {
            return null;
        }

        List<ShoppingListDataDTO> listDto = new ArrayList<>();
        for(ShoppingList s: list){
            listDto.add(ShoppingListDTOMapper.shoppingListToShoppingListDataDTO(s));
        }
        return listDto;
    }

    public static User unwrapUser(Optional<User> entity){
        if(entity.isPresent()) return entity.get();
        throw new EntityNotFoundException("User doesn't exist");

    }

    public static UUID getUserIdFromJWT(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String userId = new String(decoder.decode(token.split("\\.")[1])).split(",")[2];
        userId = userId.substring(userId.indexOf(":")+2, userId.indexOf("}")-1);
        return UUID.fromString(userId);
    }
}
