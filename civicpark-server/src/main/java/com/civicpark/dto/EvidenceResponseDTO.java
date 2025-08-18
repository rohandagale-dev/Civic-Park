package com.civicpark.dto;

import java.time.LocalDateTime;

import org.mapstruct.Mapping;

import com.civicpark.enums.MediaType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceResponseDTO {
	private Long evidenceId;
	private MediaType mediaType;
	private String mediaUrl;
	private LocalDateTime createdAt;
	private Long reportId;
}
