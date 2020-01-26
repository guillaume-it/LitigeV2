package com.ruscassie.litige.mapper;

import java.util.Optional;

import com.ruscassie.litige.dto.User;

public class UserMapper {

	public static com.ruscassie.litige.entity.User mapper(final com.ruscassie.litige.dto.User dto) {
		if (dto == null) {
			return null;
		}
		final com.ruscassie.litige.entity.User entity = new com.ruscassie.litige.entity.User();
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setId(dto.getId());
		entity.setLastName(dto.getLastName());
		entity.setPhone(dto.getPhone());

		return entity;
	}

	public static com.ruscassie.litige.dto.User mapper(final com.ruscassie.litige.entity.User entity) {
		if (entity == null) {
			return null;
		}
		final User dto = new User();
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setId(entity.getId());
		dto.setLastName(entity.getLastName());
		dto.setPhone(entity.getPhone());

		return dto;
	}

	public static Optional<User> mapper(final Optional<com.ruscassie.litige.entity.User> entity) {
		if (entity.isPresent()) {
			return Optional.of(mapper(entity.get()));
		}
		return Optional.empty();
	}

}
