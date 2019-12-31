package com.ruscassie.litige.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ServiceMapper<DTO, ENTITY> {

	final DozerBeanMapper mapper = new DozerBeanMapper();

	public ENTITY mapDtoToEntity(final DTO entity, final Class<ENTITY> destinationClass) {
		return mapper.map(entity, destinationClass);
	}

	public DTO mapEntityToDto(final ENTITY entity, final Class<DTO> destinationClass) {
		return mapper.map(entity, destinationClass);

	}

	public Page<DTO> mapper(final Page<ENTITY> pageableEntity, final Class<DTO> destinationClass) {

		final List<DTO> users = pageableEntity.getContent().stream().map(user -> {
			return mapper.map(user, destinationClass);
		}).collect(Collectors.toList());

		final Page<DTO> pageableDto = new PageImpl<>(users, pageableEntity.getPageable(),
				pageableEntity.getTotalElements());

		return pageableDto;
	}
}
