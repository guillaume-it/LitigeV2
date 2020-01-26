package com.ruscassie.litige.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.dto.Claim;
import com.ruscassie.litige.service.LitigeService;
import com.ruscassie.litige.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/litiges")
@Slf4j
@Validated
public class LitigeController {

	@Autowired
	private LitigeService litigeService;

	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('can_create_claim')")
	@PostMapping()
	public Claim create(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final Optional<com.ruscassie.litige.dto.User> userDto = userService.findByEmail(auth.getName());
		claim.setRequerant(userDto.get());
		return litigeService.save(claim);
	}

	@PreAuthorize("hasAuthority('can_delete_claim')")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") final Long id) {
		litigeService.delete(id);
	}

	@PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(path = "/{id}")
	public Claim findOne(@PathVariable final long id) {
		return litigeService.findOne(id);
	}

	@PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(value = "")
	public Page<Claim> list(final Pageable pageable) {
		return litigeService.findAll(pageable);
	}

	@PreAuthorize("hasAuthority('can_update_claim')")
	@PutMapping()
	public Claim update(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final Optional<com.ruscassie.litige.dto.User> userDto = userService.findByEmail(auth.getName());
		claim.setRequerant(userDto.get());
		return litigeService.save(claim);
	}

}
