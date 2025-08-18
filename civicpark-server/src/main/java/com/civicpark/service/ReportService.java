package com.civicpark.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.custom_exceptions.UserExceptionHandler;
import com.civicpark.dto.ReportResponseDTO;
import com.civicpark.entities.Evidence;
import com.civicpark.entities.User;
import com.civicpark.entities.VerifiedReport;
import com.civicpark.mapper.ReportMapper;
import com.civicpark.repository.ReportRepository;
import com.civicpark.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final ReportMapper reportMapper;

	// ==================== Post New Report ====================//
	@Transactional
	public ResponseEntity<?> postReport(Long id, VerifiedReport report) {
		try {

			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

			report.setUserId(id);
			// Evidence List
			if (report.getEvidences() != null) {
				for (Evidence evidence : report.getEvidences()) {
					evidence.setReport(report); // set the report reference in child
				}
			}
			VerifiedReport saved = reportRepository.save(report);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (Exception e) {
			throw new UserExceptionHandler("User not found exception");
		}

	}

	// --------------- Get Reports ---------------//
	public List<ReportResponseDTO> getReports() {
		List<VerifiedReport> reports = reportRepository.findAll();
		return reportMapper.toDtoList(reports); // MapStruct handles everything
	}

	// --------------- Get Reports By City ---------------//
	public ResponseEntity<?> getReportsByCity(String name) {
		List<?> list = reportRepository.findByCity(name);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// ==================== Find Report By Id ====================//
	public VerifiedReport findReportById(Long id) {
		try {
			VerifiedReport report = reportRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Report does not exist"));
			return report;
		} catch (Exception e) {
			throw new ResourceNotFoundException("report does not exist");
		}
	}

}
