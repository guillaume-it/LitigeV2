package com.ruscassie.litige.controller;

import java.io.IOException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Page<User>> all(@PageableDefault(size = Integer.MAX_VALUE) final Pageable pageable) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		final String role = authentication.getAuthorities().iterator().next().getAuthority();
		if (role.equals(RoleEnum.USER.name())) {
			return new ResponseEntity<Page<User>>(userService.findAllByEmail(authentication.getName(), pageable),
					HttpStatus.OK);
		}
		return new ResponseEntity<Page<User>>(userService.findAll(pageable), HttpStatus.OK);
	}

	@PutMapping("/{id}/changePassword")
	@PreAuthorize("!hasAuthority('USER') || (#oldPassword != null && !#oldPassword.isEmpty() && authentication.principal == @userRepository.findById(#id).orElse(new net.reliqs.gleeometer.users.User()).email)")
	public void changePassword(@PathVariable final Long id, @RequestParam(required = false) final String oldPassword,
			@Valid @Size(min = 3) @RequestParam final String newPassword) {
		userService.changePassword(id, oldPassword, newPassword);
	}

	@PostMapping
	@PreAuthorize("!hasAuthority('USER')")
	ResponseEntity<User> create(@Valid @RequestBody final User user) {
		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("!hasAuthority('USER')")
	public void delete(@PathVariable final Long id) {
		if (userService.existsById(id)) {
			userService.deleteById(id);
		} else {
			throw new EntityNotFoundException(User.class, "id", id.toString());
		}
	}

	@PostMapping("/validateEmail")
	public ResponseEntity<Boolean> emailExists(@RequestParam final String email) {
		return new ResponseEntity<Boolean>(userService.existsByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/findByEmail")
	@PreAuthorize("(hasAuthority('ADMIN') || hasAuthority('USER') ) && (authentication.principal == #email)")
	public ResponseEntity<User> findByEmail(@RequestParam final String email) {
		final Optional<User> user = userService.findByEmail(email);
		if (user.isPresent())
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	@PostAuthorize("!hasAuthority('USER') || (returnObject != null && returnObject.email == authentication.principal)")
	public ResponseEntity<User> one(@PathVariable final Long id) {
		final Optional<User> user = userService.findById(id);
		if (user.isPresent())
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<User>> search(@RequestParam final String email, final Pageable pageable,
			final OAuth2Authentication authentication) {
		final String auth = (String) authentication.getUserAuthentication().getPrincipal();
		final String role = authentication.getAuthorities().iterator().next().getAuthority();
		if (role.equals(RoleEnum.USER.name())) {
			return new ResponseEntity<Page<User>>(userService.findAllByEmailContainsAndEmail(email, auth, pageable),
					HttpStatus.OK);
		}
		return new ResponseEntity<Page<User>>(userService.findByEmailContains(email, pageable), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<User> signin(@RequestParam final String email, @RequestParam final String password) {
		return new ResponseEntity<User>(userService.signin(email, password), HttpStatus.OK);
	}

	@PostMapping("/signin-claimant")
	public ResponseEntity<User> signinClaimant(@RequestBody final User user) throws MessagingException, IOException {
		return new ResponseEntity<User>(userService.signinClaimant(user), HttpStatus.OK);
	}

	// || (authentication.principal == @userRepository.findById(#id).orElse(new
	// net.reliqs.gleeometer.users.User()).email)"
	@PutMapping
	@PreAuthorize("!hasAuthority('USER')")
	public void update(@Valid @RequestBody final User user) {
		userService.update(user);
	}

	@GetMapping("/validate-account")
	public ResponseEntity<User> validAccount(@RequestParam final String email, @RequestParam final String token) {

		final Optional<User> user = userService.validAccount(email, token);
		if (user.isPresent())
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
}
