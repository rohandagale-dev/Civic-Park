package com.civicpark.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressId;

	@Column(name = "flat_number", length = 20)
	private Double flatNo;

	@Column(name = "street_number", length = 20)
	private Double streetNo;

	@Column(name = "street_name", length = 50)
	private String streetName;

	@Column(name = "pincode", length = 6)
	private String pincode;
	
	@Column(name = "city")
	private String city;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "District is required")
	private String district;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "State is required")
	private String state;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Country is required")
	private String country = "India";

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

}
