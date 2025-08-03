package com.civicpark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.entities.User;
import com.civicpark.entities.VerifiedReport;
import com.civicpark.repository.ReportRepository;
import com.civicpark.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;

	// --------------- Post New Report ---------------//
	public ResponseEntity<?> postReport(Long id, VerifiedReport report) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: "));

		if (user == null) {
			throw new ResourceNotFoundException("user not found");
		} else {
			VerifiedReport newReport = reportRepository.save(report);
			return ResponseEntity.status(HttpStatus.CREATED).body(newReport);
		}
	}

	// --------------- Get Reports ---------------//
	public ResponseEntity<?> getReports() {
		List<?> list = reportRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// --------------- Get Reports By City ---------------//
	public ResponseEntity<?> getReportsByCity(String name) {
		List<?> list = reportRepository.findByCity(name);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
