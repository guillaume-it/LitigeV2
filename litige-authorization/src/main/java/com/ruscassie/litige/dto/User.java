package com.ruscassie.litige.dto;

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

	private Role role;
}