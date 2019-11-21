package com.ruscassie.litige.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.error.EntityNotFoundException;
import com.ruscassie.litige.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	final DozerBeanMapper mapper = new DozerBeanMapper();

	public void changePassword(final Long id, final String oldPassword, final String newPassword) {
		final com.ruscassie.litige.entity.User user = userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(com.ruscassie.litige.entity.User.class, "id", id.toString()));
		if (oldPassword == null || oldPassword.isEmpty() || passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
		} else {
			throw new ConstraintViolationException("old password doesn't match", new HashSet<>());
		}
	}

	public void deleteById(final Long id) {
		userRepository.deleteById(id);

	}

	public Boolean existsByEmail(final String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean existsById(final Long id) {
		return userRepository.existsById(id);
	}

	public Page<User> findAll(final Pageable pageable) {
		return mapper(userRepository.findAll(pageable));
	}

	public Page<User> findAllByEmail(final String email, final Pageable pageable) {
		return mapper(userRepository.findByEmailContains(email, pageable));
	}

	public Page<User> findAllByEmailContainsAndEmail(final String email, final String auth, final Pageable pageable) {
		return mapper(userRepository.findAllByEmailContainsAndEmail(email, auth, pageable));
	}

	public Optional<User> findByEmail(final String email) {

		final Optional<com.ruscassie.litige.entity.User> entity = userRepository.findByEmail(email);

		return Optional.of(mapper.map(entity.get(), User.class));

	}

	public Page<User> findByEmailContains(final String email, final Pageable pageable) {
		return mapper(userRepository.findByEmailContains(email, pageable));
	}

	public Optional<User> findById(final Long id) {
		final Optional<com.ruscassie.litige.entity.User> entity = userRepository.findById(id);

		return Optional.of(mapper.map(entity.get(), User.class));
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final com.ruscassie.litige.entity.User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("User not found: " + username));
		final GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Arrays.asList(authority));

	}

	private Page<User> mapper(final Page<com.ruscassie.litige.entity.User> pageableEntity) {
		final List<User> users = pageableEntity.getContent().stream().map(user -> {
			return mapper.map(user, User.class);
		}).collect(Collectors.toList());

		final Page<User> pageableDto = new PageImpl<>(users, pageableEntity.getPageable(),
				pageableEntity.getTotalElements());

		return pageableDto;
	}

	public User save(@Valid final User user) {
		return mapper.map(userRepository.save(mapper.map(user, com.ruscassie.litige.entity.User.class)), User.class);
	}

	public void update(final Long id, @Valid final User res) {
		final com.ruscassie.litige.entity.User u = userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(com.ruscassie.litige.entity.User.class, "id", id.toString()));
		// res.setGlee(u.getGlee());

		final com.ruscassie.litige.entity.User save = mapper.map(res, com.ruscassie.litige.entity.User.class);
		save.setPassword(u.getPassword());

		userRepository.save(save);

	}
}
