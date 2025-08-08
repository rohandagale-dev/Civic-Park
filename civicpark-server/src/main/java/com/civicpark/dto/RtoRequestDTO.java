package com.civicpark.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RtoRequestDTO {

	private Long rtoId;

	private String name;

	private String pincode;

	private String district;

	private String city;

	private String state;

	private AddressRequestDTO address;

	private String contactNumber;

	private String email;
	
	private String password;

	private String officerInCharge;

	private LocalDateTime registeredOn;

	private String status; 
}
