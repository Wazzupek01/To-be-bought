package com.pedrycz.tobebought.unit;

import com.pedrycz.tobebought.entities.ShoppingList;
import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.shoppingList.ShoppingListDataDTO;
import com.pedrycz.tobebought.model.user.UserDataDTO;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.model.user.mappers.UserUserDataDTOMapperImpl;
import com.pedrycz.tobebought.repositories.UserRepository;
import com.pedrycz.tobebought.services.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserUserDataDTOMapperImpl userDataDTOMapper;

    @Test
    public void loginUserTest(){
        // Given
        UserLoginDTO user = new UserLoginDTO();
        user.setUsername("User1");
        user.setPassword("Password!123");

        // When
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(new User(UUID.fromString("00000000-0000-0000-0000-000000000001"), "User1", encoder.encode("Password!123"), "wp@wp.pl", null)));
        UserLoginDTO result = userService.loginUser(user.getUsername());

        // Then
        assertEquals(user.getUsername(), result.getUsername());
        assertThrows(EntityNotFoundException.class, () -> userService.loginUser("NonExisting404"));
    }

    @Test
    public void getUserByIdTest(){
        // Given
        User user = new User();
        user.setEmail("wp@wp.pl");
        user.setUsername("User1");
        user.setPassword(encoder.encode("Password!123"));
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000069"));
        UserDataDTO userDataDTO = userDataDTOMapper.userToUserDataDTO(user);

        // When
        when(userRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000069"))).thenReturn(Optional.of(user));
        UserDataDTO result = userService.getUser(user.getId());

        // Then
        assertEquals(userDataDTO, result);
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void getUserByUsernameTest(){
        // Given
        User user = new User();
        user.setEmail("wp@wp.pl");
        user.setUsername("User1");
        user.setPassword(encoder.encode("Password!123"));
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000069"));
        UserDataDTO userDataDTO = userDataDTOMapper.userToUserDataDTO(user);

        // When
        when(userRepository.findByUsername("User1")).thenReturn(Optional.of(user));
        UserDataDTO result = userService.getUser(user.getUsername());

        // Then
        assertEquals(userDataDTO, result);
        assertThrows(EntityNotFoundException.class, () -> userService.getUser("NonExisting"));
    }

    @Test
    public void saveUserTest() {
        // Given
        UserRegisterDTO newUser = new UserRegisterDTO("User2", "Password!123", "wp@wp.pl");
        User newUserInModel = new User();
        newUserInModel.setUsername("User2");
        newUserInModel.setPassword(encoder.encode("Password!123"));
        newUserInModel.setEmail("wp@wp.pl");
        newUserInModel.setId(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // When
        UserDataDTO result = userService.saveUser(newUser);

        // Then
        assertEquals(userDataDTOMapper.userToUserDataDTO(newUserInModel), result);
    }

    @Test
    public void updateUserTest(){
        // Given
        User user = new User();
        user.setEmail("wp@wp.pl");
        user.setUsername("User1");
        user.setPassword(encoder.encode("Password!123"));
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000069"));

        // When
        when(userRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000069"))).thenReturn(Optional.of(user));
        UserDataDTO result = userService.updateUser(UUID.fromString("00000000-0000-0000-0000-000000000069"), "NewUsername1", "NewPassword!123","new@wp.pl");

        // Then
        assertEquals("NewUsername1", result.getUsername());
        assertEquals("new@wp.pl", result.getEmail());
    }

    @Test
    public void deleteUserTest() {
        // Given
        User user = new User();
        user.setEmail("wp@wp.pl");
        user.setUsername("User1");
        user.setPassword(encoder.encode("Password!123"));
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000069"));

        // When
        userService.deleteUser(user.getId());

        // Then
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void getUsersListsTest() {
        // Given
        List<ShoppingList> lists = Arrays.asList(new ShoppingList("First List"), new ShoppingList("Second List"));
        User user1 = new User(UUID.fromString("00000000-0000-0000-0000-000000000001"), "User1", encoder.encode("Password!123"), "wp@wp.pl", lists);
        User user2 = new User(UUID.fromString("00000000-0000-0000-0000-000000000002"), "User2", encoder.encode("Password!123"), "wp2@wp.pl", null);

        // when
        when(userRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001"))).thenReturn(Optional.of(user1));
        when(userRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002"))).thenReturn(Optional.of(user2));
        List<ShoppingListDataDTO> result1 = userService.getUsersLists(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        List<ShoppingListDataDTO> result2 = userService.getUsersLists(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        // then
        assertEquals(2, result1.size());
        assertNull(result2);
    }
}
