package com.ruscassie.litige.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.entity.Litige;
import com.ruscassie.litige.service.LitigeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/litige")
@Slf4j
@Validated
public class LitigeController {

	@Autowired
	private LitigeService litigeService;

	@RequestMapping(value = "/litige", method = RequestMethod.POST)
	public Litige create(@RequestBody final Litige litige) {
		return litigeService.save(litige);
	}

	@RequestMapping(value = "/litige/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") final Long id) {
		litigeService.delete(id);
	}

	@RequestMapping(value = "/litige/{id}", method = RequestMethod.GET)
	public Litige findOne(@PathVariable final long id) {
		return litigeService.findOne(id);
	}

	@RequestMapping(value = "/litiges", method = RequestMethod.GET)
	public List<Litige> list() {
		return litigeService.findAll();
	}

	@RequestMapping(value = "/litige/{id}", method = RequestMethod.PUT)
	public Litige update(@PathVariable final long id, @RequestBody final Litige litige) {
		litige.setId(id);
		return litigeService.save(litige);
	}

}
