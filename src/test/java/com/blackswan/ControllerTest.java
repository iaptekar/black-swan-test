package com.blackswan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private UserRepository repository;

	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testGetAll() throws Exception {
		MvcResult result = mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
		assertThat(Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(), User[].class)))
				.isEqualTo(repository.findAll());
	}

	@Test
	public void testCrud() throws Exception {
		testInsert();
		testUpdate();
		testDelete();
	}

	public void testInsert() throws Exception {
		mvc.perform(post("/users/user").param("firstName", "Test").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Test"))
				.andExpect(jsonPath("$.id").value("12"));
		assertThat(repository.count()).isEqualTo(12);
		assertThat(repository.existsById(12)).isEqualTo(true);
		User user = repository.getOne(12);
		assertThat(user.getFirstName()).isEqualTo("Test");
	}

	public void testUpdate() throws Exception {
		mvc.perform(
				put("/users/user").param("id", "12").param("firstName", "Tester").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Tester"));
		assertThat(repository.count()).isEqualTo(12);
		User user = repository.getOne(12);
		assertThat(user.getFirstName()).isEqualTo("Tester");
	}

	public void testDelete() throws Exception {
		mvc.perform(delete("/users/user/12").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		assertThat(repository.count()).isEqualTo(11);
		assertThat(repository.existsById(12)).isEqualTo(false);
	}
}
