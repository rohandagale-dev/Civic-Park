package com.civicpark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.entities.VerifiedReport;
import com.civicpark.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rto-office/report")
@AllArgsConstructor
public class ReportController {

	private final ReportService reportService;

	// ==================== Add New Report ====================//
	@PostMapping("/post/{id}")
	public ResponseEntity<?> postReport(@PathVariable Long id, @RequestBody VerifiedReport report) {
		try {
			return reportService.postReport(id, report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
		}
	}

	// ==================== Get Reports ====================//
	@GetMapping
	public ResponseEntity<?> getReports() {
		return ResponseEntity.status(HttpStatus.OK).body(reportService.getReports());
	}

	// --------------- Get Reports By City ---------------//
	@GetMapping("/city/{name}")
	public ResponseEntity<?> getReportsByCity(@PathVariable String name) {
		return reportService.getReportsByCity(name);
	}

	// ==================== Find Report By Id ====================//
	@GetMapping("/post/get/{id}")
	public ResponseEntity<?> findReportById(@PathVariable Long id) {
		try {
			VerifiedReport report = reportService.findReportById(id);
			return ResponseEntity.status(HttpStatus.OK).body(report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}
}
