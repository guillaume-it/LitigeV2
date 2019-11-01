package com.ruscassie.litige.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Users {

	private long id;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private long salary;

	private int age;
}