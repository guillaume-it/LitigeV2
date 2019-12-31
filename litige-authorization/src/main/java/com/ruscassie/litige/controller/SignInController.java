package com.ruscassie.litige.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/signin")
public class SignInController {

	@Autowired
	private UserService userService;

	@PostMapping("/validateEmail")
	Boolean emailExists(@RequestParam final String email) {
		return userService.existsByEmail(email);
	}

	@PostMapping
	User signin(@RequestParam final String email, @RequestParam final String password) {
		return userService.signin(email, password);
	}

}
