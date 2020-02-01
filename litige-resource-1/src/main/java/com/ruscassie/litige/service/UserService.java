package com.ruscassie.litige.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.entity.User;
import com.ruscassie.litige.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
}