package com.ruscassie.litige.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ruscassie.litige.entity.Claim;
import com.ruscassie.litige.entity.Dossier;
import com.ruscassie.litige.entity.User;
import com.ruscassie.litige.repository.ClaimRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClaimServiceTest {

	@InjectMocks
	private ClaimService claimService;

	@Mock
	private ClaimRepository claimRepository;
//	@Autowired
//	private StorageService storageService;

	@Before
	public void setUp() {
//        MockitoAnnotations.initMocks(this);

		final User agent = new User();
		agent.setId(1L);
		agent.setFirstName("First Name agent");

		final User claimant = new User();
		claimant.setFirstName("First Name claimant");

		final Dossier dossier = new Dossier();

		final Optional<Claim> claim = Optional.of(new Claim());
		claim.get().setId(1L);
		claim.get().setCreation(ZonedDateTime.now().minusHours(4));
		claim.get().setMessage("Message");
		claim.get().setModification(ZonedDateTime.now().plusHours(4));
		claim.get().setObjet("Objet");
		claim.get().setAgent(agent);
		claim.get().setClaimant(claimant);
		claim.get().setDossier(dossier);

		Mockito.when(claimRepository.findById(1L)).thenReturn(claim);
	}

	@Test
	public void whenValidId_theClaimShouldBeFound() {
		final Optional<Claim> claim = claimService.findOne(1L);
		assertTrue(claim.isPresent());
		assertEquals(claim.get().getId(), new Long(1L));
	}

}
