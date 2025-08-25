package com.civicpark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.ReportCreateRequestDTO;
import com.civicpark.dto.ReportResponseDTO;
import com.civicpark.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rto-office/report")
@AllArgsConstructor

/**
 * Report controller handles all methods related to report
 */
public class ReportController {
	private final ReportService reportService;

	// ==========================================================================================//
	/**
	 * Post new report
	 * 
	 * @param id
	 * @param report
	 * @return 201 created
	 */
	@PostMapping("/post/{id}")
	public ResponseEntity<?> postReport(@PathVariable Long id, @RequestBody ReportCreateRequestDTO dto) {
		try {
			reportService.postReport(id, dto);
			return ResponseEntity.ok(HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ResourceConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ==========================================================================================//
	/**
	 * List of report only accessible to authorized RTO office
	 * 
	 * @return reports
	 */
	@GetMapping
	public ResponseEntity<?> getReports() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getReports());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("list is empty");
		}
	}

	// ============================================================================//
	/**
	 * Find report by id
	 * 
	 * @param id
	 * @return report
	 */
	@GetMapping("/post/get/{id}")
	public ResponseEntity<?> findReportById(@PathVariable Long id) {
		try {
			ReportResponseDTO report = reportService.findReportById(id);
			return ResponseEntity.status(HttpStatus.OK).body(report);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// =======================================================================//
	@GetMapping("/post/number/{number}")
	public ResponseEntity<?> findReportByVehicleNumber(@PathVariable String number) {
		try {
			List<ReportResponseDTO> report = reportService.findReportByVehicleNumber(number);
			return ResponseEntity.status(HttpStatus.OK).body(report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("post/status/pending")
	public ResponseEntity<?> findReportByStatusPending() {
		try {
			List<ReportResponseDTO> report = reportService.findReportByStatusPending();
			return ResponseEntity.status(HttpStatus.OK).body(report);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body("something went wrong");
		}
	}

	@GetMapping("/count")
	public ResponseEntity<?> getThisMonthReportsCount() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getThisMonthReportSummary());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
