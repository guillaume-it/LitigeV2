package com.ruscassie.litige.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruscassie.litige.entity.FileInformation;

public interface FileInformationRepository extends PagingAndSortingRepository<FileInformation, Long> {

	public Optional<FileInformation> findByIdAndClaims_Id(Long idFile, Long idClaim);

}
