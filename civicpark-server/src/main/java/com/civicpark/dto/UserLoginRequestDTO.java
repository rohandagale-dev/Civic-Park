package com.civicpark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

/**
 * DTO for loggin user, only accepts "email" and "password" from front-end
 */
public class UserLoginRequestDTO {
	private String email;
	private String password;
}
