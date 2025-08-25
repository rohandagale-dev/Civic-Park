package com.civicpark.service;

import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
public class S3Service {

	private final S3Presigner presigner;

	@Value("${AWS_BUCKET}")
	private String bucketName;

	@Value("${AWS_REGION}")
	private String region;

	public S3Service(S3Presigner presigner) {
		this.presigner = presigner;
	}

	// ==========================================================================//
	/**
	 * Generate pre-signed PUT URL for uploading a file to S3
	 */
	public URL generatePresignedUploadUrl(String fileName, String contentType) {
		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileName)
				.contentType(contentType).build();

		PresignedPutObjectRequest presignedRequest = presigner
				.presignPutObject(p -> p.putObjectRequest(objectRequest).signatureDuration(Duration.ofMinutes(10)));

		return presignedRequest.url();
	}

	// =============================================================================================================//
	/**
	 * Generate pre-signed GET URL for downloading/viewing a file from S3
	 */
	public String generatePresignedGetUrl(String objectKey) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectKey).build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().getObjectRequest(getObjectRequest)
				.signatureDuration(Duration.ofMinutes(60)).build();

		return presigner.presignGetObject(presignRequest).url().toString();
	}
}
