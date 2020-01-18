package com.ruscassie.litige.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.Litige;
import com.ruscassie.litige.mapper.LitigeMapper;
import com.ruscassie.litige.mapper.UserMapper;
import com.ruscassie.litige.repository.LitigeRepository;

@Service
public class LitigeService {

	@Autowired
	private LitigeRepository litigeRepository;

	final ServiceMapper<Litige, com.ruscassie.litige.entity.Litige> serviceMapper = new ServiceMapper<>();

	public void delete(final long id) {
		litigeRepository.deleteById(id);
	}

	public Page<Litige> findAll(final Pageable pageable) {
		return serviceMapper.mapper(litigeRepository.findAll(pageable), Litige.class);
	}

	public Litige findOne(final long id) {
		return serviceMapper.mapEntityToDto(litigeRepository.findById(id).get(), Litige.class);
	}

	public Litige save(final Litige litige) {
		final com.ruscassie.litige.entity.Litige eLitige = LitigeMapper.mapper(litige);
		eLitige.setAgent(UserMapper.mapper(litige.getAgent()));
		eLitige.setRequerant(UserMapper.mapper(litige.getRequerant()));

		return serviceMapper.mapEntityToDto(litigeRepository.save(eLitige), Litige.class);
	}
}
