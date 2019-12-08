package com.ruscassie.litige.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ruscassie.litige.entity.Litige;


public interface LitigeRepository extends PagingAndSortingRepository<Litige, Long> {

}
