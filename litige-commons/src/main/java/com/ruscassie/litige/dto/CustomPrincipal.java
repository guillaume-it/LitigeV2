package com.ruscassie.litige.dto;

import java.io.Serializable;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomPrincipal implements Serializable {

	private String username;
	private String email;

}
