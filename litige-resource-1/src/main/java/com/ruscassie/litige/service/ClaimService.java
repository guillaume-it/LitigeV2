package com.ruscassie.litige.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dto.Claim;
import com.ruscassie.litige.dto.FileInformation;
import com.ruscassie.litige.mapper.ClaimMapper;
import com.ruscassie.litige.mapper.FileInformationMapper;
import com.ruscassie.litige.mapper.UserMapper;
import com.ruscassie.litige.repository.ClaimRepository;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository litigeRepository;

	public Optional<Claim> addFileInformation(final Long idClaim, final FileInformation fileInformation) {
		final Optional<com.ruscassie.litige.entity.Claim> claim = litigeRepository.findById(idClaim);
		if (claim.isPresent()) {
			if (claim.get().getFileInformations() == null) {
				claim.get().setFileInformations(new ArrayList<com.ruscassie.litige.entity.FileInformation>());
			}
			claim.get().getFileInformations().add(FileInformationMapper.mapper(fileInformation));
			return Optional.of(ClaimMapper.mapper(litigeRepository.save(claim.get())));
		}
		return Optional.empty();

	}

	public void delete(final long id) {
		litigeRepository.deleteById(id);
	}

	public Page<Claim> findAll(final Pageable pageable) {

		final Page<com.ruscassie.litige.entity.Claim> pageClaim = litigeRepository.findAll(pageable);

		final List<Claim> claims = pageClaim.getContent().stream().map(claim -> {
			return ClaimMapper.mapper(claim);
		}).collect(Collectors.toList());

		final Page<Claim> pageableDto = new PageImpl<>(claims, pageClaim.getPageable(), pageClaim.getTotalElements());

		return pageableDto;

	}

	public Claim findOne(final long id) {
		return ClaimMapper.mapper(litigeRepository.findById(id).get());
	}

	public Claim save(final Claim litige) {
		final com.ruscassie.litige.entity.Claim eLitige = ClaimMapper.mapper(litige);
		eLitige.setAgent(UserMapper.mapper(litige.getAgent()));
		eLitige.setClaimant(UserMapper.mapper(litige.getRequerant()));

		return ClaimMapper.mapper(litigeRepository.save(eLitige));
	}
}
