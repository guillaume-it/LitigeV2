package com.ruscassie.litige.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruscassie.litige.entity.FileInformation;

public interface FileInformationRepository extends PagingAndSortingRepository<FileInformation, Long> {

}
