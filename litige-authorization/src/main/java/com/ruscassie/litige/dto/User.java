package com.ruscassie.litige.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
// https://www.jmdoudoux.fr/java/dej/chap-validation_donnees.htm
	private Long id;

	@NotEmpty()
	@Size(min = 3, max = 20)
	@Pattern(regexp = "^([a-zA-Z]|-)+$")
	private String firstName;

	@Size(min = 3, max = 20)
	@NotEmpty()
	@Pattern(regexp = "^([a-zA-Z]|-)+$")
	private String lastName;

	@Size(min = 12, max = 14)
	@NotEmpty()
	@Pattern(regexp = "^(\\+237|237)?[0-9]{9}$")
	private String phone;

	@Size(min = 4, max = 70)
	@NotEmpty()
	@Email()
	private String email;

	@Size(min = 3, max = 200)
	private String password;

	private List<Role> roles;
}