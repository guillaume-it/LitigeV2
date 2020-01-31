package com.ruscassie.litige.mapper;

import com.ruscassie.litige.dto.FileInformation;

public class FileInformationMapper {

	public static FileInformation mapper(final com.ruscassie.litige.entity.FileInformation entity) {
		final FileInformation dto = new FileInformation();
		dto.setContentType(entity.getContentType());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setSize(entity.getSize());
		dto.setUri(entity.getUri());

		return dto;
	}

	public static com.ruscassie.litige.entity.FileInformation mapper(final FileInformation dto) {
		final com.ruscassie.litige.entity.FileInformation entity = new com.ruscassie.litige.entity.FileInformation();
		entity.setContentType(dto.getContentType());
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setSize(dto.getSize());
		entity.setUri(dto.getUri());

		return entity;
	}
}
