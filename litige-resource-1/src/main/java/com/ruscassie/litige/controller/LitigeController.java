package com.ruscassie.litige.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.dto.Litige;
import com.ruscassie.litige.service.LitigeService;
import com.ruscassie.litige.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/litiges")
@Slf4j
//@Validated
public class LitigeController {

	@Autowired
	private LitigeService litigeService;

	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Litige create(@RequestBody final Litige litige) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final Optional<com.ruscassie.litige.dto.User> userDto = userService.findByEmail(auth.getName());
		litige.setRequerant(userDto.get());
		return litigeService.save(litige);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") final Long id) {
		litigeService.delete(id);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping(path = "/{id}")
	public Litige findOne(@PathVariable final long id) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return litigeService.findOne(id);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "")
	public Page<Litige> list(final Pageable pageable) {
		return litigeService.findAll(pageable);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Litige update(@PathVariable final long id, @RequestBody final Litige litige) {
		litige.setId(id);
		return litigeService.save(litige);
	}

}
