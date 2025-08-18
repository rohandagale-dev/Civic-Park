package com.civicpark.service;

import java.net.URL;
import java.time.Duration;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class S3Service {

//    @Value("${aws.s3.bucket}")
    private String bucketName;

//    @Value("${aws.s3.access-key}")
    private String accessKey;

//    @Value("${aws.s3.secret-key}")
    private String secretKey;

//    @Value("${aws.s3.region}")
    private String region;

    public URL generatePresignedUrl(String fileName, String contentType) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(p -> p.signatureDuration(Duration.ofMinutes(10))
                        .putObjectRequest(objectRequest));

        presigner.close();
        return presignedRequest.url();
    }
}
