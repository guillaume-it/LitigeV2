package com.ruscassie.litige.mapper;

import com.ruscassie.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

	public static com.ruscassie.litige.entity.User mapper(final User dto) {
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

	public static User mapper(final com.ruscassie.litige.entity.User entity) {
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

	public static Page<User> mapper(Page<com.ruscassie.litige.entity.User> ePage) {

		if(ePage != null) {
			return new PageImpl<>(mapper(ePage.getContent()));
		}
		return null;
	}

	private static List<User> mapper(List<com.ruscassie.litige.entity.User> users) {
		if(users != null){
			return users.stream().map(user -> mapper(user)).collect(Collectors.toList());
		}
		return null;
	}
}
