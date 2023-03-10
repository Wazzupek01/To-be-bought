package com.pedrycz.tobebought.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pedrycz.tobebought.TestConstants.TEST_USERS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

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
	public void createValidUserTest() throws Exception {
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("User10", "Password!123", "user@gmail.com"));

		RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	public void createExistingUsernameTest() throws Exception {
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("User1", "Password!123", "email@wp.pl"));

		RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void createExistingEmailTest() throws Exception {
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("User10", "Password!123", "wp@wp.pl"));

		RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void createInvalidPasswordTest() throws Exception {
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("User10", "Password123", "wp@wp.pl"));

		RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void createInvalidUsernameTest() throws Exception {
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("usr", "Password!123", "wp@wp.pl"));

		RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void findByIdTest() throws Exception {
		Cookie jwtTokenCookie = getJWT();

		RequestBuilder request = MockMvcRequestBuilders.get("/user/").cookie(jwtTokenCookie);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value(TEST_USERS.get(0).getUsername()))
				.andExpect(jsonPath("$.email").value(TEST_USERS.get(0).getEmail()));
	}

	@Test
	public void updateUserTest() throws Exception{
		Cookie jwtTokenCookie = getJWT();
		String object = objectMapper.writeValueAsString(new UserRegisterDTO("User1", "Password@2137", "wp@wp.pl"));

		RequestBuilder request = MockMvcRequestBuilders.put("/user/").cookie(jwtTokenCookie)
				.content(object).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("User1"));
	}

	private Cookie getJWT() throws Exception  {
		String object = objectMapper.writeValueAsString(new UserLoginDTO(1L,"User1", "Password!123"));

		RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(object);

		MvcResult loginResult = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andExpect(cookie().exists("jwt-token")).andReturn();
		return loginResult.getResponse().getCookie("jwt-token");
	}

}
