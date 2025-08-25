package com.civicpark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/*
 * User registration request DTO to add new user in database
 * 
 * 
 */
public class UserRegistrationRequestDTO {
	@NotBlank(message = "First name is missing (DTO)")
	private String firstName;

	@NotBlank(message = "Middle name is missing (DTO)")
	private String middleName;

	@NotBlank(message = "Last name is missing (DTO)")
	private String lastName;

	@NotBlank(message = "email is missing (DTO)")
	private String email;

	@NotBlank(message = "password is missing (DTO)")
	private String password;

	@Size(min = 10)
	private String contactNumber;

	@NotBlank(message = "address is missing (DTO)")
	private AddressRequestDTO address;
}