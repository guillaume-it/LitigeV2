package com.ruscassie.litige.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Litige {

	private Long id;
	private String objet;
	private String message;
	private Date creation;
	private User requerant;
	private User agent;

}
