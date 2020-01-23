package com.ruscassie.litige.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruscassie.litige.entity.Claim;


public interface ClaimRepository extends PagingAndSortingRepository<Claim, Long> {

}
