package com.civicpark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class UpdatePasswordRequest {
	@NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
	private String password;
}
	