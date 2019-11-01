package com.ruscassie.litige.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Litige {

	private Long id;
	private String objet;
	private String localite;
	private Date creation;
	private String requerant;
	private String agent;

}
