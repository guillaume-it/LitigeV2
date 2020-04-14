package com.ruscassie.litige.dto;

import org.springframework.core.io.Resource;

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
	private Resource resource;
}