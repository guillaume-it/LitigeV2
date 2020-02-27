package com.ruscassie.litige.controller;


import com.ruscassie.litige.repository.ClaimRepository;
import com.ruscassie.litige.service.ClaimService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { WebMvcConfiguration.class })
//@WebAppConfiguration
//@AutoConfigureMockMvc
@SpringBootTest
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
	private static Connection conn;
	private static Liquibase liquibase;
	@Mock
	private ClaimRepository claimRepository;
//	@BeforeAll
//	public static void createTestData() throws SQLException,
//			ClassNotFoundException, LiquibaseException {
//
//		Class.forName("org.h2.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
//
//		Database database = DatabaseFactory.getInstance()
//				.findCorrectDatabaseImplementation(new JdbcConnection(conn));
//
//		liquibase = new Liquibase("../liquibase/master.xml",
//				new FileSystemResourceAccessor(), database);
//		liquibase.dropAll();
//		liquibase.update("test");
//	}

	@AfterAll
	public static void removeTestData() throws LiquibaseException, SQLException {
	//	liquibase.rollback(1000, "test");
	//	conn.close();
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
