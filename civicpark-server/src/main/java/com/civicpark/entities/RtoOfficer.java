//package com.civicpark.entities;
//
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "rto_officers")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//
//public class RtoOfficer {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "officer_id")
//	private Long officerId;
//
//	@Column(name = "name", nullable = false, length = 255)
//	private String name;
//
//	@Column(name = "email", nullable = false, length = 50)
//	private String email;
//
//	@Column(name = "password", nullable = false)
//	@NotBlank(message = "password should not be blank")
//	@Size(min = 8, max = 64)
//	private String password;
//
//	@Column(name = "contact_number", nullable = false)
//	private String contactNumber;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "designation", nullable = false)
//	private DesignationType designation;
//
//	@OneToOne
//	@JoinColumn(name = "rto_id", referencedColumnName = "rto_id", nullable = false)
//	private RtoOffice rtoId;
//
//	@Column(name = "profile_photo_url")
//	private String profilePhotoUrl;
//
//	@CreationTimestamp
//	@Column(updatable = false)
//	private LocalDateTime createdAt;
//
//	@UpdateTimestamp
//	private LocalDateTime updatedAt;
//}
