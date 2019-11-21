package com.ruscassie.litige.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.entity.Litige;
import com.ruscassie.litige.repository.LitigeRepository;

@Service
public class LitigeService {

	@Autowired
	private LitigeRepository litigeRepository;

	public void delete(final long id) {
		litigeRepository.deleteById(id);
	}

	public List<Litige> findAll() {
		final List<Litige> list = new ArrayList<>();
		litigeRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public Litige findOne(final long id) {
		return litigeRepository.findById(id).get();
	}

	public Litige save(final Litige user) {
		return litigeRepository.save(user);
	}
}
