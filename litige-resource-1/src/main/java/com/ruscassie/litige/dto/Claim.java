package com.ruscassie.litige.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Claim {

	private Long id;
	@NotEmpty
	private String objet;
	@NotEmpty
	private String message;
	private ZonedDateTime creation;
	private User requerant;
	private User agent;

}
