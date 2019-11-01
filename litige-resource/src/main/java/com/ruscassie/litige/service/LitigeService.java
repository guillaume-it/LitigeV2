package com.ruscassie.litige.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dao.LitigeDao;
import com.ruscassie.litige.entity.Litige;


@Service(value = "litigeService")
public class LitigeService {
	
	@Autowired
	private LitigeDao litigeDao;


	public List<Litige> findAll() {
		List<Litige> list = new ArrayList<>();
		litigeDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public Litige findOne(long id) {
		return litigeDao.findById(id).get();
	}

	public void delete(long id) {
		litigeDao.deleteById(id);
	}

    public Litige save(Litige user) {
        return litigeDao.save(user);
    }
}
