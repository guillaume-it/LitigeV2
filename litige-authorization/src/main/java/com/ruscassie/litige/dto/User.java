package com.ruscassie.litige.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

	private Long id;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private Role role;
}