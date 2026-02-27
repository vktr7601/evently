package com.evently.events.infrastructure.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, S3Folders folder) {
        String key =
                folder.getFolderName() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest request =
                    PutObjectRequest.builder().bucket(bucketName).key(key).contentType(file.getContentType()).contentLength(file.getSize()).build();

            s3Client.putObject(request,
                    RequestBody.fromInputStream(file.getInputStream(),
                            file.getSize()));

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }

        return buildFileUrl(key);
    }

    public void deleteFile(String fileUrl) {
        String key = fileUrl.substring(fileUrl.indexOf(".amazonaws.com/") +
                ".amazonaws.com/".length());

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    private String buildFileUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }
}