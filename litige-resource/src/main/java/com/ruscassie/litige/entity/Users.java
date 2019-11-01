package com.ruscassie.litige.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private long salary;
	@Column
	private int age;
}
