package com.ruscassie.litige.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.entity.Role;
import com.ruscassie.litige.error.EntityNotFoundException;
import com.ruscassie.litige.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final ServiceMapper<User, com.ruscassie.litige.entity.User> serviceMapper = new ServiceMapper<>();

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
		return serviceMapper.mapper(userRepository.findAll(pageable), User.class);
	}

	public Page<User> findAllByEmail(final String email, final Pageable pageable) {
		return serviceMapper.mapper(userRepository.findByEmailContains(email, pageable), User.class);
	}

	public Page<User> findAllByEmailContainsAndEmail(final String email, final String auth, final Pageable pageable) {
		return serviceMapper.mapper(userRepository.findAllByEmailContainsAndEmail(email, auth, pageable), User.class);
	}

	public Optional<User> findByEmail(final String email) {

		final Optional<com.ruscassie.litige.entity.User> entity = userRepository.findByEmail(email);
		return Optional.of(serviceMapper.mapEntityToDto(entity.get(), User.class));

	}

	public Page<User> findByEmailContains(final String email, final Pageable pageable) {
		return serviceMapper.mapper(userRepository.findByEmailContains(email, pageable), User.class);
	}

	public Optional<User> findById(final Long id) {
		final Optional<com.ruscassie.litige.entity.User> entity = userRepository.findById(id);

		return Optional.of(serviceMapper.mapEntityToDto(entity.get(), User.class));
	}

	@Override
	public UserDetails loadUserByUsername(final String input) {
		Optional<com.ruscassie.litige.entity.User> user = null;

//		if (input.contains("@"))
		user = userRepository.findByEmail(input);
		System.err.println(user.toString());
//		else
//			user = userRepository.findByUsername(input);

		if (!user.isPresent())
			throw new BadCredentialsException("Bad credentials");

		new AccountStatusUserDetailsChecker().check(user.get());

		return user.get();
	}

//	@Override
//	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//		final com.ruscassie.litige.entity.User user = userRepository.findByEmail(username)
//				.orElseThrow(() -> new RuntimeException("User not found: " + username));
//		final GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().get(0).getName());
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				Arrays.asList(authority));
//
//	}

	public User save(@Valid final User user) {
		return serviceMapper.mapEntityToDto(
				userRepository.save(serviceMapper.mapDtoToEntity(user, com.ruscassie.litige.entity.User.class)),
				User.class);
	}

	public User signin(final String email, final String password) {
		final Role role = new Role();
		role.setName("user");
		final com.ruscassie.litige.entity.User u = new com.ruscassie.litige.entity.User();

		u.setId(null);
		u.setEmail(email);
		u.setPassword(passwordEncoder.encode(password));
		u.setRoles(Arrays.asList(role));
		return serviceMapper.mapEntityToDto(userRepository.save(u), User.class);
	}

	public void update(final Long id, @Valid final User res) {
		final com.ruscassie.litige.entity.User u = userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(com.ruscassie.litige.entity.User.class, "id", id.toString()));
		// res.setGlee(u.getGlee());

		final com.ruscassie.litige.entity.User save = serviceMapper.mapDtoToEntity(res,
				com.ruscassie.litige.entity.User.class);
		save.setPassword(u.getPassword());

		userRepository.save(save);

	}

}
