package com.civicpark.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import com.civicpark.enums.ReportStatus;
import com.civicpark.enums.ReportType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long reportId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "vehicle_number")
	private String vehicleNumber;

	@Column(name = "vehicle_color")
	private String vehicleColor;

	@Column(name = "report_type")
	@Enumerated(EnumType.STRING)
	private ReportType reportType;

	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Evidence> evidences = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
	private Address address;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	private ReportStatus reportStatus = ReportStatus.PENDING;

	@Column(name = "verified_by")
	private Long verifiedBy;
}