package com.civicpark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civicpark.entities.VerifiedReport;

public interface ReportRepository extends JpaRepository<VerifiedReport, Long> {
	public List<?> findByCity(String city);
}
