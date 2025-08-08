package com.civicpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civicpark.entities.RtoOffice;


public interface RtoOfficeRepository extends JpaRepository<RtoOffice, Long> {
	public RtoOffice findByEmail(String email);
}
