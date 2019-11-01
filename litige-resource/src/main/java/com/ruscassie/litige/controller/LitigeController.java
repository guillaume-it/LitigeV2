package com.ruscassie.litige.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.entity.Litige;
import com.ruscassie.litige.service.LitigeService;

@RestController
@RequestMapping("/litige")
public class LitigeController {

    @Autowired
    private LitigeService litigeService;

    @RequestMapping(value="/litiges", method = RequestMethod.GET)
    public List<Litige> list(){
        return litigeService.findAll();
    }

    @RequestMapping(value = "/litige", method = RequestMethod.POST)
    public Litige create(@RequestBody Litige litige){
        return litigeService.save(litige);
    }

    @RequestMapping(value = "/litige/{id}", method = RequestMethod.GET)
    public Litige findOne(@PathVariable long id){
        return litigeService.findOne(id);
    }

    @RequestMapping(value = "/litige/{id}", method = RequestMethod.PUT)
    public Litige update(@PathVariable long id, @RequestBody Litige litige){
    	litige.setId(id);
        return litigeService.save(litige);
    }

    @RequestMapping(value = "/litige/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id){
    	litigeService.delete(id);
    }

}
