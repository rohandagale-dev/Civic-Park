package com.civicpark.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.ReportCreateRequestDTO;
import com.civicpark.dto.ReportResponseDTO;
import com.civicpark.dto.ReportSummaryDTO;
import com.civicpark.entities.Evidence;
import com.civicpark.entities.Report;
import com.civicpark.enums.ReportStatus;
import com.civicpark.mapper.ReportMapper;
import com.civicpark.repository.ReportRepository;
import com.civicpark.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

/**
 * Resport service handles operations like post, list reports
 */
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final ReportMapper reportMapper;
	private final S3Service s3Service;

	// ===========================================================================================================//
	/**
	 * Verify existed user and insert new report in db
	 * 
	 * @param id
	 * @param report
	 * @return response
	 */
	@Transactional
	public ReportResponseDTO postReport(Long id, ReportCreateRequestDTO dto) {
		// Check if user exist
		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		// Check for duplicate report
		Report checkReportExists = reportRepository.findByVehicleNumber(dto.getVehicleNumber());

		if (checkReportExists != null) {
			throw new ResourceConflictException("A report with number" + dto.getVehicleNumber() + "already exists");
		}

		Report report = reportMapper.toEntity(dto);

		// Evidence List
		if (report.getEvidences() != null) {
			for (Evidence evidence : report.getEvidences()) {
				evidence.setReport(report);
			}
		}

		Report saved = reportRepository.save(report);
		ReportResponseDTO responseDTO = reportMapper.toDto(saved);
		return responseDTO;
	}

	// =========================================================================//
	/**
	 * Fetch list of reports only accessible to authorized RTO Office
	 * 
	 * @return reports
	 */
	public List<ReportResponseDTO> getReports() {
		List<Report> reports = reportRepository.findAll();
		return reports.stream().map((item) -> reportMapper.toDto(item)).toList();
	}

	// =====================================================================================//
	/**
	 * Returns report by id. Evidence "media_url" field is set with AWS pre-signed
	 * url
	 * 
	 * @param id
	 * @return report
	 */
	public ReportResponseDTO findReportById(Long id) {
		Report report = reportRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Report does not exist"));

		// Generate pre-signed URLs for all evidences
		report.getEvidences().forEach(evidence -> {
			String presignedUrl = s3Service.generatePresignedGetUrl(evidence.getObjectKey());
			evidence.setMediaUrl(presignedUrl); // Use a separate field in Evidence entity for URL
		});

		// Map to DTO
		ReportResponseDTO responseDTO = reportMapper.toDto(report);
		System.out.println(responseDTO.getAddress());
		return responseDTO;
	}

	// ===============================================================//
	/**
	 * Find report by vehicle number
	 * 
	 * @param number
	 * @return report
	 */
	public List<ReportResponseDTO> findReportByVehicleNumber(String number) {

		// Last 4 digit search
		if (number.length() == 4) {
			return reportRepository.findByVehicleNumberEndingWith(number).stream().map(item -> reportMapper.toDto(item))
					.toList();
		}

		Report report = reportRepository.findByVehicleNumber(number);

		if (report == null) {
			throw new ResourceNotFoundException("A report with number: " + number + " does not exist");
		}

		return List.of(reportMapper.toDto(report));
	}

	// =========================================================//
	/**
	 * Find reports by report status "PENDING" (enum value)
	 * 
	 * @return
	 */
	public List<ReportResponseDTO> findReportByStatusPending() {
		List<Report> reportList = reportRepository.findByReportStatus(ReportStatus.PENDING);

		if (reportList == null) {
			throw new ResourceNotFoundException("no reports are found with status pending");
		}

		return reportList.stream().map((item) -> reportMapper.toDto(item)).toList();
	}

	public ReportSummaryDTO getThisMonthReportSummary() {
		LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();

		LocalDateTime end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

		return reportRepository.countReportsSummary(start, end);
	}

}
