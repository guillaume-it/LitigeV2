package com.ruscassie.litige.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ruscassie.litige.entity.Claim;
import com.ruscassie.litige.entity.FileInformation;
import com.ruscassie.litige.repository.ClaimRepository;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private StorageService storageService;

	public Optional<FileInformation> addFileInformation(final Long idClaim, final MultipartFile file) {
		final Optional<Claim> claim = claimRepository.findById(idClaim);
		if (claim.isPresent()) {

			final FileInformation fileInformation = storageService.create(file, claim.get().getId().toString());

			if (claim.get().getFileInformations() == null) {
				claim.get().setFileInformations(new ArrayList<FileInformation>());
			}
			claim.get().getFileInformations().add(fileInformation);
			claimRepository.save(claim.get());
			return Optional.of(fileInformation);
		}
		return Optional.empty();

	}

	public void delete(final long id) {
		claimRepository.deleteById(id);
	}

	public Page<Claim> findAll(final Pageable pageable) {

		final Page<com.ruscassie.litige.entity.Claim> pageClaim = claimRepository.findAll(pageable);

		return pageClaim;

	}

	public Optional<Claim> findOne(final long id) {
		return claimRepository.findById(id);
	}

	public boolean removeFileInformation(final Long idClaim, final Long idFileInformation) {
		final Optional<Claim> claim = claimRepository.findById(idClaim);
		if (claim.isPresent()) {
			final Optional<FileInformation> optional = claim.get().getFileInformations().stream()
					.filter(fileInformation -> fileInformation.getId().equals(idFileInformation)).findFirst();
			if (optional.isPresent()) {

				return storageService.delete(optional.get());
			}

		}
		return false;

	}

	public Claim save(final Claim claim) {

		return claimRepository.save(claim);
	}
}
