package com.ruscassie.litige.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
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
import org.springframework.transaction.annotation.Transactional;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.entity.Permission;
import com.ruscassie.litige.error.EntityNotFoundException;
import com.ruscassie.litige.mapper.UserMapper;
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

	@Autowired
	private EmailService emailService;

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
		final User dto = serviceMapper.mapEntityToDto(entity.get(), User.class);
		return Optional.of(dto);

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
		Optional<com.ruscassie.litige.entity.User> user = userRepository.findByEmail(input);

		if (!user.isPresent())
			throw new BadCredentialsException("Bad credentials");

		new AccountStatusUserDetailsChecker().check(user.get());

		return user.get();
	}

	private void oauthClient(final com.ruscassie.litige.entity.User user) {
		final BaseClientDetails client = new BaseClientDetails();
		//TODO Conf properties
		//client.setClientId(user.getEmail().toString());
		client.setClientId("ClientIdResource");
		client.setClientSecret(user.getPassword());
		client.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token", "implicit"));
		final List<Permission> permissions = user.getRoles().stream().flatMap(role -> role.getPermissions().stream())
				.collect(Collectors.toList());
		client.setScope(permissions.stream().filter(permission -> permission.getName().startsWith("scope_"))
				.map(role -> role.getName()).collect(Collectors.toList()));
		//TODO Conf properties
		client.setAccessTokenValiditySeconds(5 * 60);
		client.setRefreshTokenValiditySeconds(30 * 24 * 60 * 60);
		client.setResourceIds(Arrays.asList("claim/resource"));
		jdbcClientDetailsService.addClientDetails(client);
	}

	public User save(@Valid final User user) {
		return serviceMapper.mapEntityToDto(
				userRepository.save(serviceMapper.mapDtoToEntity(user, com.ruscassie.litige.entity.User.class)),
				User.class);
	}

	@Transactional(rollbackFor = Exception.class)
	public User signin(final String email, final String password) {
		final com.ruscassie.litige.entity.User user = new com.ruscassie.litige.entity.User();

		// TODO enity
		final com.ruscassie.litige.entity.Role roleAgent = roleRepository.findByName("role_agent");
		final com.ruscassie.litige.entity.Role roleAdmin = roleRepository.findByName("role_admin");

		user.setEmail(email);
		user.setPassword("{bcrypt}" + passwordEncoder.encode(password));
		user.setRoles(Arrays.asList(roleAgent, roleAdmin));

		userRepository.saveAndFlush(user);

		oauthClient(user);

		return serviceMapper.mapEntityToDto(user, User.class);
	}

	@Transactional(rollbackFor = Exception.class)
	public User signinClaimant(final User user) throws MessagingException, IOException {

		final com.ruscassie.litige.entity.User eUser = new com.ruscassie.litige.entity.User();
		final com.ruscassie.litige.entity.Role roleClaimant = roleRepository.findByName("role_claimant");
		eUser.setLastName(user.getLastName());
		eUser.setFirstName(user.getFirstName());
		eUser.setPhone(user.getPhone());
		eUser.setEmail(user.getEmail());
		eUser.setPassword("{bcrypt}" + passwordEncoder.encode(user.getPassword()));
		eUser.setRoles(Arrays.asList(roleClaimant));
		eUser.setTokenActiveAccount(UUID.randomUUID().toString());

		userRepository.saveAndFlush(eUser);

		emailService.prepareAndSend(eUser);
		oauthClient(eUser);

		final User dto = serviceMapper.mapEntityToDto(eUser, User.class);
		dto.setPassword(null);
		return dto;

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

	// TODO make a batch to clear unactivate account
	public Optional<User> validAccount(final String email, final String tokenActiveAccount) {
		final Optional<com.ruscassie.litige.entity.User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			if (user.get().getTokenActiveAccount().equals(tokenActiveAccount)) {
				user.get().setEnabled(true);
				user.get().setTokenActiveAccount(null);
				return Optional.of(UserMapper.mapper(userRepository.save(user.get())));
			}
		}
		return Optional.empty();
	}

}
