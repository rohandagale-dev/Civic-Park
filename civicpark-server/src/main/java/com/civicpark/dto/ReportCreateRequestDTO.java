package com.civicpark.dto;

import java.util.List;

import com.civicpark.enums.ReportStatus;
import com.civicpark.enums.ReportType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateRequestDTO {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotBlank(message = "Vehicle number is required")
	private String vehicleNumber;

	@NotBlank(message = "Vehicle color is required")
	private String vehicleColor;

	@NotNull(message = "Report type is required")
	private ReportType reportType;

	@Size(min = 1, message = "At least one evidence is required")
	private List<EvidenceCreateRequestDTO> evidences;

	@NotBlank(message = "Report status is required")
	private ReportStatus reportStatus;

	@NotNull(message = "Address is required")
	private AddressRequestDTO address;

	private String longitude;

	private String latitude;

	private Long verifiedBy;

}
