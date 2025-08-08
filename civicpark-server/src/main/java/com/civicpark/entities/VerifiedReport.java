package com.civicpark.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.civicpark.enums.ReportStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verified_reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class VerifiedReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long reportId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Evidence> evidences = new ArrayList<>();


	@Column(name = "city")
	private String city;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "latitude")
	private String latitude;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	private ReportStatus reportStatus;

	@Column(name = "verified_by")
	private Long verifiedBy;
}
