package com.ruscassie.litige.controller;


import com.ruscassie.litige.repository.ClaimRepository;
import com.ruscassie.litige.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ClaimControllerTest {

	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	// https://www.baeldung.com/integration-testing-in-spring
	@InjectMocks
	private ClaimController claimController;

	@Autowired
	private  MockMvc mockMvc;

	@Mock
	private ClaimService claimService;

	@Mock
	private ClaimRepository claimRepository;


	@WithMockUser(authorities = "can_read_claim")
	@Test
	public void test() throws Exception {

		final MvcResult result = mockMvc
				.perform(get("/litiges/1")).andDo(print())
				.andExpect(status().isOk()).andReturn();

		final String resultDOW = result.getResponse().getContentAsString();
		assertNotNull(resultDOW);


	}

}
