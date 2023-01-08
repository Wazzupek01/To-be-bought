package com.pedrycz.tobebought.services;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.model.user_mappers.UserUserDataDTOMapper;
import com.pedrycz.tobebought.model.user_mappers.UserUserLoginDTOMapper;
import com.pedrycz.tobebought.model.user_mappers.UserUserRegisterDTOMapper;
import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserUserDataDTOMapper dataDTOMapper = Mappers.getMapper(UserUserDataDTOMapper.class);
    private UserUserRegisterDTOMapper registerDTOMapper = Mappers.getMapper(UserUserRegisterDTOMapper.class);
    private UserUserLoginDTOMapper loginDTOMapper = Mappers.getMapper(UserUserLoginDTOMapper.class);

    @Override
    public UserLoginDTO loginUser(String username) {
        User user = unwrapUser(userRepository.findByUsername(username));
        return loginDTOMapper.userToUserLoginDTO(user);
    }

    @Autowired
    public UserServiceImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDataDTO getUser(Long id) {
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
    public UserDataDTO updateUser(Long id, String username, String password, String email) {
        User user = unwrapUser(userRepository.findById(id));
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
        return dataDTOMapper.userToUserDataDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<ShoppingList> getUsersLists(Long id) {
        User user = unwrapUser(userRepository.findById(id));
        return user.getShoppingLists();
    }

    static User unwrapUser(Optional<User> entity){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("User doesn't exist");

    }

    public static Long getUserIdFromJWT(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String userId = new String(decoder.decode(token.split("\\.")[1])).split(",")[2];
        userId = userId.substring(userId.indexOf(":")+1, userId.indexOf("}"));
        Long uid = Long.parseLong(userId);
        return uid;
    }
}
