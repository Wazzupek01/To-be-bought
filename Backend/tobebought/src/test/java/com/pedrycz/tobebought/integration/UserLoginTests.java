package com.pedrycz.tobebought.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pedrycz.tobebought.TestConstants.TEST_USERS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.saveAll(TEST_USERS);
    }

    @AfterEach
    void clear() {
        userRepository.deleteAll(userRepository.findAll());
    }

    @Test
    public void loginValidUserCredentialsTest() throws Exception {
        String object = objectMapper.writeValueAsString(new UserLoginDTO(1L,"User1", "Password!123"));

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful()).andExpect(cookie().exists("jwt-token"));
    }

    @Test()
    public void loginInvalidPasswordTest() throws Exception {
        String object = objectMapper.writeValueAsString(new UserLoginDTO(1L,"User1", "Password#123"));

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized()).andExpect(cookie().doesNotExist("jwt-token"));
    }

    @Test
    public void loginIneligiblePasswordTest() throws Exception {
        String object = objectMapper.writeValueAsString(new UserLoginDTO(1L,"User1", "password"));

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object);
        mockMvc.perform(request)
                .andExpect(result -> result.getResponse().getContentAsString().contains("Incorrect password"))
                .andExpect(status().isUnauthorized()).andExpect(cookie().doesNotExist("jwt-token"));
    }

    @Test
    public void loginIneligibleUsernameTest() throws Exception {
        String object = objectMapper.writeValueAsString(new UserLoginDTO(1L,"usr", "Password!123"));

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object);
        mockMvc.perform(request)
                .andExpect(result -> result.getResponse().getContentAsString().contains("Username doesn't exist"))
                .andExpect(status().isUnauthorized()).andExpect(cookie().doesNotExist("jwt-token"));
    }
}
