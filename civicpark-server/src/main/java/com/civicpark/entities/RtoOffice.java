//package com.civicpark.entities;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "rto_offices")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//
//public class RtoOffice {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "rto_id")
//	private Long rtoId;
//    
//    @Column(nullable = false, length = 255)
//    private String name;
//
//    @Column(name = "pincode", nullable = false, length = 6)
//    private String pincode;
//
//    @Column(name = "district",nullable = false, length = 50)
//    private String district;
//
//    @Column(nullable = false, length = 255)
//    private String city;
//
//    @Column(nullable = false, length = 255)
//    private String state;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
//    private Address address;
//
//    @Column(name = "contact_number", length = 20)
//    private String contactNumber;
//
//    @Column(length = 255)
//    private String email;
//
//    @Column(name = "officer_in_charge", length = 255)
//    private String officerInCharge;
//
//    @Column(name = "registered_on", nullable = false)
//    private LocalDateTime registeredOn;
//
////    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, length = 20)
//    private String status;
//
//
//   
//}
