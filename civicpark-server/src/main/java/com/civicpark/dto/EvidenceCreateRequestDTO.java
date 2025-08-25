package com.civicpark.dto;

import java.time.LocalDateTime;

import com.civicpark.enums.MediaType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceCreateRequestDTO {
	private Long evidenceId;

	@NotNull(message = "media type should not be empty")
	private MediaType mediaType;

	@NotNull(message = "media url is required")
	private String mediaUrl;

	@NotBlank(message = "object key is required")
	private String objectKey;

	private LocalDateTime createdAt;
}
