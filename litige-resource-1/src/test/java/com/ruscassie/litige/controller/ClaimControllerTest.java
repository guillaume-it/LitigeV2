package com.ruscassie.litige.controller;


import com.ruscassie.litige.repository.ClaimRepository;
import com.ruscassie.litige.service.ClaimService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@ContextConfiguration(classes = { WebMvcConfiguration.class })
@WebAppConfiguration

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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

	@BeforeAll
	public  static void setUp() {

	}

	@WithMockUser(username = "admin@admin.com", roles = { "can_read_claim" })
	@Test
	public void test() throws Exception {

		final MvcResult result = mockMvc
				.perform(get("/litiges/1")).andDo(print())
				.andExpect(status().isOk()).andReturn();

		final String resultDOW = result.getResponse().getContentAsString();
		assertNotNull(resultDOW);


	}
}
