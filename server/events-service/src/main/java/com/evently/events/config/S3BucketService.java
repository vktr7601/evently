package com.evently.events.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3BucketService {
    private final String bucketName = "evently-spring";
    private final S3Client s3Client;

    public String uploadFile(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileName).contentType(file.getContentType()).build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName).build());
    }
}