package com.ruscassie.litige.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private final ServiceMapper<User, com.ruscassie.litige.entity.User> serviceMapper = new ServiceMapper<>();

	public Optional<User> findByEmail(final String email) {
		return serviceMapper.mapEntityToDto(userRepository.findByEmail(email), User.class);
	}
}