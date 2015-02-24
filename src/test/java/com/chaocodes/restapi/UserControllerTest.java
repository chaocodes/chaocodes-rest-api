package com.chaocodes.restapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.chaocodes.restapi.model.User;
import com.chaocodes.restapi.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest extends ControllerTest
{
	private MockMvc mockMvc;

	private String baseUrl = "/user";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private User testUser;

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		userRepository.deleteAll();
		testUser = userRepository.save(new User("test@test.com", "test"));
	}

	@Test
	public void createOneUser() throws Exception {
		String email = "testpost@test.com";
		String username = "testpost";
		User createUser = new User(email, username);
		mockMvc.perform(post(baseUrl)
			.content(json(createUser))
			.contentType(contentType))
			.andExpect(status().isCreated());
		User checkUser = userRepository.findByUsername(username);
		Assert.assertNotNull(checkUser);
		Assert.assertEquals(email, checkUser.getEmail());
	}

	@Test
	public void readAllUsers() throws Exception {
		mockMvc.perform(get(baseUrl))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(testUser.getId().intValue())))
			.andExpect(jsonPath("$[0].email", is(testUser.getEmail())))
			.andExpect(jsonPath("$[0].username", is(testUser.getUsername())))
			.andExpect(jsonPath("$[0].owner", is(testUser.isOwner())));
	}

	@Test
	public void readOneUser() throws Exception {
		mockMvc.perform(get(baseUrl + "/" + testUser.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.id", is(testUser.getId().intValue())))
			.andExpect(jsonPath("$.email", is(testUser.getEmail())))
			.andExpect(jsonPath("$.username", is(testUser.getUsername())))
			.andExpect(jsonPath("$.owner", is(testUser.isOwner())));
	}

	@Test
	public void readOneUserNotFound() throws Exception {
		mockMvc.perform(get(baseUrl + "/5"))
			.andExpect(status().isNotFound());
	}

	@Test
	public void updateOneUser() throws Exception {
		User updateUser = userRepository.save(new User("testupdate@test.com", "testupdate"));
		User updatedUser = new User("testupdated@test.com", "testupdated");
		mockMvc.perform(put(baseUrl + "/" + updateUser.getId())
			.content(json(updatedUser))
			.contentType(contentType))
			.andExpect(status().isOk());
		User checkUser = userRepository.findOne(updateUser.getId());
		Assert.assertNotNull(checkUser);
		Assert.assertEquals(updatedUser.getEmail(), checkUser.getEmail());
		Assert.assertEquals(updatedUser.getUsername(), checkUser.getUsername());
	}

	@Test
	public void deleteOneUser() throws Exception {
		User deleteUser = userRepository.save(new User("testdelete@test.com", "testdelete"));
		mockMvc.perform(delete(baseUrl + "/" + deleteUser.getId()))
			.andExpect(status().isNoContent());
		User checkUser = userRepository.findOne(deleteUser.getId());
		Assert.assertNull(checkUser);
	}
}
