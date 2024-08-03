package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.service.MinioService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @Author Liu Jialin
 * @Date 2024/7/18 21:53
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName MinioServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String uploadFile(String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
        try {
             minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build());

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(7, TimeUnit.DAYS) // URL will be valid for 7 days
                    .build()); // Return MinIO object URL
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    @Override
    public ObjectWriteResponse uploadFileSimple(String bucketName, String objectName, MultipartFile multipartFile) {
        try {
            return minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .contentType(multipartFile.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    public void ensureBucketExists(String bucketName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Check if the bucket exists
        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        // Create the bucket if it does not exist
        if (!bucketExists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
            System.out.println("Bucket created: " + bucketName);
        } else {
            System.out.println("Bucket already exists: " + bucketName);
        }
    }
}
