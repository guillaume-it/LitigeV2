package com.ruscassie.litige.service;

import java.util.List;

import com.ruscassie.litige.model.Users;

public interface UserService {

    Users save(Users user);
    List<Users> findAll();
    Users findOne(long id);
    void delete(long id);
}
