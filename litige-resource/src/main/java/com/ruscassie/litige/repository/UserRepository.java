package com.ruscassie.litige.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruscassie.litige.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	Boolean existsByEmail(String email);

	Page<User> findAllByEmail(String email, Pageable pageable);

	Page<User> findAllByEmailContainsAndEmail(String email, String auth, Pageable pageable);

	Optional<User> findByEmail(String email);

	Page<User> findByEmailContains(String email, Pageable pageable);
}
