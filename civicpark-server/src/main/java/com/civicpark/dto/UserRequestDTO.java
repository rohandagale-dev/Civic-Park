package com.civicpark.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDTO {
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String contactNumber;
	private Date registeredOn;
	private AddressRequestDTO address;
}	