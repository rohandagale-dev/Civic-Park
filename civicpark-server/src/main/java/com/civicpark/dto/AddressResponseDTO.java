package com.civicpark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {

    private Double flatNo;

    private Double streetNo;

    private String streetName;

    private String pincode;

    private String city;

    private String district;

    private String state;

    private String country = "India";

    private Double latitude;

    private Double longitude;
}
