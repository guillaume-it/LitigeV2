package com.ruscassie.litige.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

	private Long id;

	private String firstName;

	private String lastName;

	private String phone;

	private String email;

	private String password;

	private List<Role> roles;
}