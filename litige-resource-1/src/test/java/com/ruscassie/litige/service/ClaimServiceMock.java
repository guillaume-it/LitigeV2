package com.ruscassie.litige.service;

import com.ruscassie.litige.entity.Claim;
import com.ruscassie.litige.entity.Dossier;
import com.ruscassie.litige.repository.ClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ClaimServiceMock {

	@InjectMocks
	private ClaimService claimService;

	@Mock
	private ClaimRepository claimRepository;

	@BeforeEach
	public void setUp() {
//        MockitoAnnotations.initMocks(this);

//		final User agent = new User();
//		agent.setId(1L);
//		agent.setFirstName("First Name agent");
//
//		final User claimant = new User();
//		claimant.setFirstName("First Name claimant");

		final Dossier dossier = new Dossier();

		final Optional<Claim> claim = Optional.of(new Claim());
		claim.get().setId(1L);
		claim.get().setCreation(ZonedDateTime.now().minusHours(4));
		claim.get().setMessage("Message");
		claim.get().setModification(ZonedDateTime.now().plusHours(4));
		claim.get().setObjet("Objet");
		claim.get().setAgentId(1l);
		claim.get().setClaimantId(2l);
		claim.get().setDossier(dossier);

		final List<Claim> claims = new ArrayList<Claim>();
		claims.add(claim.get());

		final Page<Claim> page = new PageImpl<>(claims);

//		final Page<com.ruscassie.litige.entity.Claim> pageClaim = Page.empty(PageRequest.of(0, 3, Sort.by("id")));
		// pageClaim.getContent().add(0, claim.get());

		lenient().when(claimRepository.findById(1L)).thenReturn(claim);
		lenient().when(claimRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
				.thenReturn(page);

	}

	@Test
	public void whenValidId_theClaimShouldBeDelete() {
		try {
			claimService.delete(1L);

		} catch (final IllegalArgumentException e) {
			fail("Should not have thrown any exception");
		}
	}

	@Test
	public void whenValidId_theClaimShouldBeFound() {
		final Optional<Claim> claim = claimService.findOne(1L);
		assertTrue(claim.isPresent());
		assertEquals(claim.get().getId(), new Long(1L));
	}

	@Test
	public void whenValidPageable_thistClaimsShouldBeFound() {
		final Page<Claim> page = claimService.findAll(PageRequest.of(0, 3, Sort.by("id")));
		assertEquals(page.getNumberOfElements(), 1L);
	}
}
