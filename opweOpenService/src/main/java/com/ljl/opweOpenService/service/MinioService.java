package com.ljl.opweOpenService.service;

import io.minio.ObjectWriteResponse;
import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Liu Jialin
 * @Date 2024/7/18 21:52
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName MinioService
 * @Description TODO
 * @Version 1.0.0
 */
public interface MinioService {
    String uploadFile(String bucketName, String objectName, InputStream inputStream, long size, String contentType);

    ObjectWriteResponse uploadFileSimple(String bucketName, String objectName, MultipartFile multipartFile);

    void ensureBucketExists(String bucketName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException;

    InputStream fetchFile(String bucketName, String fileName);
}
