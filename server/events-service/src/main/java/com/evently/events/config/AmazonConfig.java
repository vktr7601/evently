package com.evently.events.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonConfig {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .region(Region.EU_NORTH_1) // Change to your bucket's region
            .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    "AKIA4HB7X5DXB45HRBQ7", // Ideally use @Value("${aws.access-key}")
                    "EL6N3hXqKqC43wRxecADLVcB4z2uFQr5sOJJdy90"  // Ideally use @Value("${aws.secret-key}")
                )
            ))
            .build();
    }
}