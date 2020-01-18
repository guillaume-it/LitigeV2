package com.ruscassie.litige.controller;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.dto.RoleEnum;
import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.error.EntityNotFoundException;
import com.ruscassie.litige.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	Page<User> all(@PageableDefault(size = Integer.MAX_VALUE) final Pageable pageable) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		final String role = authentication.getAuthorities().iterator().next().getAuthority();
		if (role.equals(RoleEnum.USER.name())) {
			return userService.findAllByEmail(authentication.getName(), pageable);
		}
		return userService.findAll(pageable);
	}

	@PutMapping("/{id}/changePassword")
	@PreAuthorize("!hasAuthority('USER') || (#oldPassword != null && !#oldPassword.isEmpty() && authentication.principal == @userRepository.findById(#id).orElse(new net.reliqs.gleeometer.users.User()).email)")
	void changePassword(@PathVariable final Long id, @RequestParam(required = false) final String oldPassword,
			@Valid @Size(min = 3) @RequestParam final String newPassword) {
		userService.changePassword(id, oldPassword, newPassword);
	}

	@PostMapping
	@PreAuthorize("!hasAuthority('USER')")
	User create(@Valid @RequestBody final User res) {
		return userService.save(res);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("!hasAuthority('USER')")
	void delete(@PathVariable final Long id) {
		if (userService.existsById(id)) {
			userService.deleteById(id);
		} else {
			throw new EntityNotFoundException(User.class, "id", id.toString());
		}
	}

	@PostMapping("/validateEmail")
	Boolean emailExists(@RequestParam final String email) {
		return userService.existsByEmail(email);
	}

	@GetMapping("/findByEmail")
	@PreAuthorize("(hasAuthority('ADMIN') || hasAuthority('USER') ) && (authentication.principal == #email)")
	User findByEmail(@RequestParam final String email) {
		return userService.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));
	}

	@GetMapping("/{id}")
	@PostAuthorize("!hasAuthority('USER') || (returnObject != null && returnObject.email == authentication.principal)")
	User one(@PathVariable final Long id) {
		return userService.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
	}

	@GetMapping("/search")
	Page<User> search(@RequestParam final String email, final Pageable pageable,
			final OAuth2Authentication authentication) {
		final String auth = (String) authentication.getUserAuthentication().getPrincipal();
		final String role = authentication.getAuthorities().iterator().next().getAuthority();
		if (role.equals(RoleEnum.USER.name())) {
			return userService.findAllByEmailContainsAndEmail(email, auth, pageable);
		}
		return userService.findByEmailContains(email, pageable);
	}

	@PostMapping("/signin")
	User signin(@RequestParam final String email, @RequestParam final String password) {
		return userService.signin(email, password);
	}

	@PostMapping("/signin-claimant")
	User signinClaimant(@RequestParam final String email, @RequestParam final String password,
			@RequestParam final String firstName, @RequestParam final String name, @RequestParam final String phone) {

		return userService.signinClaimant(email, password, firstName, name, phone);
	}

	// || (authentication.principal == @userRepository.findById(#id).orElse(new
	// net.reliqs.gleeometer.users.User()).email)"
	@PutMapping
	@PreAuthorize("!hasAuthority('USER')")
	void update(@Valid @RequestBody final User user) {
		userService.update(user);
	}

}
