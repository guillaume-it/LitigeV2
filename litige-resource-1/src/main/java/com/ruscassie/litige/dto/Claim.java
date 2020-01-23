package com.ruscassie.litige.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Claim {

	private Long id;
	private String objet;
	private String message;
	private ZonedDateTime creation;
	private User requerant;
	private User agent;

}
