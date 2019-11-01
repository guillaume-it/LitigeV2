package com.ruscassie.litige.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.entity.Users;
import com.ruscassie.litige.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<Users> listUser(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Users create(@RequestBody Users user){
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Users findOne(@PathVariable long id){
        return userService.findOne(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Users update(@PathVariable long id, @RequestBody Users user){
        user.setId(id);
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
    }

}
