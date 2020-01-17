package com.ruscassie.litige.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
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
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.entity.Role;
import com.ruscassie.litige.error.EntityNotFoundException;
import com.ruscassie.litige.repository.RoleRepository;
import com.ruscassie.litige.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JdbcClientDetailsService jdbcClientDetailsService;

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
		this.log.error("loadUserByUsername: " + input);
		Optional<com.ruscassie.litige.entity.User> user = null;

		user = userRepository.findByEmail(input);
		this.log.error("loadUserByUsername: " + user);

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

	private void oauthClient(final com.ruscassie.litige.entity.User user) {
		final BaseClientDetails client = new BaseClientDetails();
		client.setClientId(user.getEmail().toString());
		client.setClientSecret(user.getPassword());
		client.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token", "implicit"));
		client.setScope(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
		client.setAccessTokenValiditySeconds(5 * 60);
		client.setRefreshTokenValiditySeconds(30 * 24 * 60 * 60);

		jdbcClientDetailsService.addClientDetails(client);
	}

	public User save(@Valid final User user) {
		return serviceMapper.mapEntityToDto(
				userRepository.save(serviceMapper.mapDtoToEntity(user, com.ruscassie.litige.entity.User.class)),
				User.class);
	}

	@Transactional(rollbackOn = Exception.class)
	public User signin(final String email, final String password) {
		final com.ruscassie.litige.entity.User user = new com.ruscassie.litige.entity.User();
		final Role roleUser = roleRepository.findByName("role_user");
		final Role roleAdmin = roleRepository.findByName("role_admin");

		user.setEmail(email);
		user.setPassword("{bcrypt}" + passwordEncoder.encode(password));
		user.setRoles(Arrays.asList(roleUser, roleAdmin));

		userRepository.saveAndFlush(user);

		oauthClient(user);

		return serviceMapper.mapEntityToDto(user, User.class);
	}

	@Transactional(rollbackOn = Exception.class)
	public User signinClaimant(final String email, final String password, final String firstName, final String name,
			final String phone) {

		final com.ruscassie.litige.entity.User user = new com.ruscassie.litige.entity.User();
		final Role roleClaimant = roleRepository.findByName("role_claimant");
		user.setName(name);
		user.setFirstName(firstName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword("{bcrypt}" + passwordEncoder.encode(password));
		user.setRoles(Arrays.asList(roleClaimant));

		userRepository.saveAndFlush(user);

		oauthClient(user);

		return serviceMapper.mapEntityToDto(user, User.class);

	}

	public void update(@Valid final User res) {
		final com.ruscassie.litige.entity.User u = userRepository.findById(res.getId())
				.orElseThrow(() -> new EntityNotFoundException(com.ruscassie.litige.entity.User.class, "id",
						res.getId().toString()));

		final com.ruscassie.litige.entity.User save = serviceMapper.mapDtoToEntity(res,
				com.ruscassie.litige.entity.User.class);
		save.setPassword(u.getPassword());

		userRepository.save(save);
		userRepository.flush();
	}

}
