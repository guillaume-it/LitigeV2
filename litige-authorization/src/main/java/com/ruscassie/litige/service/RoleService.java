package com.ruscassie.litige.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

}
