package com.civicpark.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import com.civicpark.dto.UpdatePasswordRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "middle_name", nullable = false)
	private String middleName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "email", nullable  = false)
	private String email;
	
	@Column(name = "password", nullable  = false)
	@NotBlank(message = "password should not be blank")
	@Size(min = 8, max = 64)
	private String password;
	
	@Column(name = "contact_number", nullable  = false)
	private String contactNumber;
	
	@Column(name = "registered_on")
	private Date registeredOn;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
	private Address address;

}
