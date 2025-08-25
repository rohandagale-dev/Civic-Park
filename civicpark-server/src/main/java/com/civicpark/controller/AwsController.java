package com.civicpark.controller;

import java.net.URL;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.service.S3Service;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AwsController {
	private final S3Service s3Service;

	// ========================================================================================================//
	/**
	 * Generate aws-s3 presigned url
	 * 
	 * @param fileName
	 * @param contentType
	 * @return presigned url
	 */
	@GetMapping("/aws-s3/presigned-url")
	public ResponseEntity<?> getPresignedUrl(@RequestParam String fileName, @RequestParam String contentType) {
		URL url = s3Service.generatePresignedUploadUrl(fileName, contentType);
		return ResponseEntity.ok(Map.of("url", url));
	}
}
