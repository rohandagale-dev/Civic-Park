package com.civicpark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class ReportSummaryDTO {
	private Long total;
	private Long pending;
	private Long closed;
}
