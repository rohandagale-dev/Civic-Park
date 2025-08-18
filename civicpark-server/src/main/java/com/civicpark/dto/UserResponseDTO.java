package com.civicpark.dto;

import java.time.LocalDateTime;

import com.civicpark.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private LocalDateTime createdAt;
    private Address address;
}
