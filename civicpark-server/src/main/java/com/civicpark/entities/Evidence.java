package com.civicpark.entities;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.civicpark.enums.MediaType;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evidence")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evidence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evidence_id")
	private Long evidenceId;

	@Enumerated(EnumType.STRING)
	@Column(name = "media_type", nullable = false)
	private MediaType mediaType;

	@Column(name = "media_url", nullable = false)
	private String mediaUrl;

	@CreationTimestamp
	@Column(updatable = false)
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "report_id", nullable = false)
	private VerifiedReport report;

}
