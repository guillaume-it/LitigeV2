package com.ruscassie.litige.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "userdb")
@NoArgsConstructor
@EqualsAndHashCode
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	@Column
	private String email;

	@Column
	private String name;

	@Column
	private String firstName;

	@Column
	private String phone;

	@Column
	private String password;

}
