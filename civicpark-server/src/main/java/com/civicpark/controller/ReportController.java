package com.civicpark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.entities.VerifiedReport;
import com.civicpark.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

	private final ReportService reportService;

	// ==================== Post New Report ====================//
	@PostMapping("/post/{id}")
	public ResponseEntity<?> postReport(@PathVariable Long id, @RequestBody VerifiedReport report) {
		return reportService.postReport(id, report);
	}

	// --------------- Get Reports ---------------//
	@GetMapping
	public ResponseEntity<?> getReports() {
		return reportService.getReports();
	}

	// --------------- Get Reports By City ---------------//
	@GetMapping("/city/{name}")
	public ResponseEntity<?> getReportsByCity(@PathVariable String name) {
		return reportService.getReportsByCity(name);
	}
}
