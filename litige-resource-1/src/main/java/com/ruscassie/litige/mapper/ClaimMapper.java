package com.ruscassie.litige.mapper;

import java.util.Optional;

import com.ruscassie.litige.dto.Claim;
import com.ruscassie.litige.dto.User;
import sun.management.Agent;

public class ClaimMapper {

	public static com.ruscassie.litige.entity.Claim mapper(final Claim dto) {
		if (dto == null) {
			return null;
		}
		final com.ruscassie.litige.entity.Claim entity = new com.ruscassie.litige.entity.Claim();
		entity.setCreation(dto.getCreation());
		entity.setId(dto.getId());
		entity.setMessage(dto.getMessage());
		entity.setObjet(dto.getObjet());
		return entity;
	}

	public static Claim mapper(final com.ruscassie.litige.entity.Claim entity) {
		if (entity == null) {
			return null;
		}
		final Claim dto = new Claim();
		dto.setCreation(entity.getCreation());
		dto.setId(entity.getId());
		dto.setMessage(entity.getMessage());
		dto.setObjet(entity.getObjet());
		dto.setAgent(new User());
		dto.getAgent().setId(entity.getAgentId());
		return dto;
	}

	public static Optional<Claim> mapper(final Optional<com.ruscassie.litige.entity.Claim> entity) {
		if (entity.isPresent()) {
			return Optional.of(mapper(entity.get()));

		}
		return Optional.empty();

	}

}
