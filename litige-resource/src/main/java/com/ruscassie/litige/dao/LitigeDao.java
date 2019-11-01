package com.ruscassie.litige.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ruscassie.litige.entity.Litige;

@Repository
public interface LitigeDao extends CrudRepository<Litige, Long> {

}
