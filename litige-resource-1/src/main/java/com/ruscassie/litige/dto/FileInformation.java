package com.ruscassie.litige.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInformation {
	private Long id;
	private String name;
	private String uri;
	private String contentType;
	private Long size;
	private Claim claim;
}