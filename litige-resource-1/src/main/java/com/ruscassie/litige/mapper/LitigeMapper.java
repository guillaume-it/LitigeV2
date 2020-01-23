package com.ruscassie.litige.mapper;

import com.ruscassie.litige.dto.Claim;

public class LitigeMapper {

	public static Claim mapper(final com.ruscassie.litige.entity.Claim entity) {
		if (entity == null) {
			return null;
		}
		final Claim dto = new Claim();
		dto.setCreation(entity.getCreation());
		dto.setId(entity.getId());
		dto.setMessage(entity.getMessage());
		dto.setObjet(entity.getObjet());
		return dto;
	}

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

}
