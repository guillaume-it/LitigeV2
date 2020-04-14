package com.ruscassie.litige.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ruscassie.litige.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Page<User> findAllByEmail(String email, Pageable pageable);

	Page<User> findAllByEmailContainsAndEmail(String email, String auth, Pageable pageable);

	Optional<User> findByEmail(String email);

	Page<User> findByEmailContains(String email, Pageable pageable);

}
