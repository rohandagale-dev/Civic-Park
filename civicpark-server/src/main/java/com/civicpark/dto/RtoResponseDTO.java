package com.civicpark.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RtoResponseDTO {

	private Long id;
	private String officeName;
	private String email;
	private String contactNumber;
	private String city;
	private String state;
	private LocalDateTime createdAt;

}