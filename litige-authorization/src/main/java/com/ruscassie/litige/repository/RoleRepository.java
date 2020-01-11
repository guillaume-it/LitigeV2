package com.ruscassie.litige.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruscassie.litige.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
