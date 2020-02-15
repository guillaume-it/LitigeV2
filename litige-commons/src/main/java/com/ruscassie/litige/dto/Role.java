package com.ruscassie.litige.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {
	private Long id;

	private String name;

	private List<Permission> permissions;

}
