package com.civicpark.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.civicpark.dto.ReportSummaryDTO;
import com.civicpark.entities.Report;
import com.civicpark.enums.ReportStatus;

public interface ReportRepository extends JpaRepository<Report, Long> {
	Report findByVehicleNumber(String vehicleNumber);

	List<Report> findByVehicleNumberEndingWith(String lastFourDigits);

	List<Report> findByReportStatus(ReportStatus reportStatus);

	 @Query("""
		        SELECT new com.civicpark.dto.ReportSummaryDTO(
		            COUNT(r),
		            SUM(CASE WHEN r.reportStatus = com.civicpark.enums.ReportStatus.PENDING THEN 1L ELSE 0L END),
		            SUM(CASE WHEN r.reportStatus = com.civicpark.enums.ReportStatus.CLOSED THEN 1L ELSE 0L END)
		        )
		        FROM Report r
		        WHERE r.createdAt BETWEEN :start AND :end
		    """)
		    ReportSummaryDTO countReportsSummary(@Param("start") LocalDateTime start,
		                                         @Param("end") LocalDateTime end);	
}
