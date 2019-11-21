package com.ruscassie.litige.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.dto.Role;
import com.ruscassie.litige.entity.User;
import com.ruscassie.litige.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/signin")
public class SignInController {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public SignInController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    User signin(@RequestParam String email, @RequestParam String password) {
        User u = new User(null, email, passwordEncoder.encode(password), Role.USER);
        return repository.save(u);
    }

    @PostMapping("/validateEmail")
    Boolean emailExists(@RequestParam String email) {
        return repository.existsByEmail(email);
    }

}
