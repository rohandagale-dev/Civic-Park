package com.civicpark.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.civicpark.enums.ReportStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {
	private Long reportId;
	private Long userId;
	private List<EvidenceResponseDTO> evidences;
	private String vehicleNumber;
	private String vehicleColor;
	private String city;
	private String longitude;
	private String latitude;
	private LocalDateTime createdAt;
	private ReportStatus reportStatus;
	private Long verifiedBy;
}
