package com.ruscassie.litige.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.Claim;
import com.ruscassie.litige.mapper.LitigeMapper;
import com.ruscassie.litige.mapper.UserMapper;
import com.ruscassie.litige.repository.ClaimRepository;

@Service
public class LitigeService {

	@Autowired
	private ClaimRepository litigeRepository;

	public void delete(final long id) {
		litigeRepository.deleteById(id);
	}

	public Page<Claim> findAll(final Pageable pageable) {

		final Page<com.ruscassie.litige.entity.Claim> pageClaim = litigeRepository.findAll(pageable);

		final List<Claim> claims = pageClaim.getContent().stream().map(claim -> {
			return LitigeMapper.mapper(claim);
		}).collect(Collectors.toList());

		final Page<Claim> pageableDto = new PageImpl<>(claims, pageClaim.getPageable(), pageClaim.getTotalElements());

		return pageableDto;

	}

	public Claim findOne(final long id) {
		return LitigeMapper.mapper(litigeRepository.findById(id).get());
	}

	public Claim save(final Claim litige) {
		final com.ruscassie.litige.entity.Claim eLitige = LitigeMapper.mapper(litige);
		eLitige.setAgent(UserMapper.mapper(litige.getAgent()));
		eLitige.setClaimant(UserMapper.mapper(litige.getRequerant()));

		return LitigeMapper.mapper(litigeRepository.save(eLitige));
	}
}
