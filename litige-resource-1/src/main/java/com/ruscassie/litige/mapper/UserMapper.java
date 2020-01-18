package com.ruscassie.litige.mapper;

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
		entity.setName(dto.getName());
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
		dto.setName(entity.getName());
		dto.setPhone(entity.getPhone());

		return dto;
	}

}
