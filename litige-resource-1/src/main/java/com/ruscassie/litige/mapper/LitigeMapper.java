package com.ruscassie.litige.mapper;

import com.ruscassie.litige.dto.Litige;

public class LitigeMapper {

	public static Litige mapper(final com.ruscassie.litige.entity.Litige entity) {
		if (entity == null) {
			return null;
		}
		final Litige dto = new Litige();
		dto.setCreation(entity.getCreation());
		dto.setId(entity.getId());
		dto.setMessage(entity.getMessage());
		dto.setObjet(entity.getObjet());
		return dto;
	}

	public static com.ruscassie.litige.entity.Litige mapper(final Litige dto) {
		if (dto == null) {
			return null;
		}
		final com.ruscassie.litige.entity.Litige entity = new com.ruscassie.litige.entity.Litige();
		entity.setCreation(dto.getCreation());
		entity.setId(dto.getId());
		entity.setMessage(dto.getMessage());
		entity.setObjet(dto.getObjet());
		return entity;
	}

}
