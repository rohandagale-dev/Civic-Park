package com.civicpark.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.civicpark.entities.Evidence;
import com.civicpark.entities.User;
import com.civicpark.entities.VerifiedReport;
import com.civicpark.repository.ReportRepository;
import com.civicpark.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;

	// ==================== Post New Report ====================//
	@Transactional
	public ResponseEntity<?> postReport(Long id, VerifiedReport report) {
	    Optional<User> userOpt = userRepository.findById(id);

	    if (userOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
	    }

	    User user = userOpt.get();

	    report.setCreatedAt(LocalDateTime.now());

	    if (report.getEvidences() != null) {
	        for (Evidence evidence : report.getEvidences()) {
	            evidence.setReport(report); // set the report reference in child
	        }
	    }

	    VerifiedReport saved = reportRepository.save(report);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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
