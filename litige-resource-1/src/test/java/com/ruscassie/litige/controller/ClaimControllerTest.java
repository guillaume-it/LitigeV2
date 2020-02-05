package com.ruscassie.litige.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ruscassie.litige.config.WebMvcConfiguration;
import com.ruscassie.litige.repository.ClaimRepository;
import com.ruscassie.litige.service.ClaimService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfiguration.class })
@WebAppConfiguration
@AutoConfigureMockMvc
public class ClaimControllerTest {

	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	@Autowired
	private WebApplicationContext wac;

	// https://www.baeldung.com/integration-testing-in-spring
	@InjectMocks
	private ClaimController claimController;

	@Autowired
	private MockMvc mockMvc;
	@Mock
	private ClaimService claimService;

	@Mock
	private ClaimRepository claimRepository;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

//	@Before
//	public void setUp() {
//
//	}

	@WithMockUser(username = "admin@admin.com", roles = { "can_read_claim" })
	@Test
	public void test() throws Exception {
		final MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/litiges/1").with(user("admin@admin.com"))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		final String resultDOW = result.getResponse().getContentAsString();
		assertNotNull(resultDOW);

	}
}