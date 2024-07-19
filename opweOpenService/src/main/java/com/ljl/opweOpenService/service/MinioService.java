package com.ljl.opweOpenService.service;

import java.io.InputStream;

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
}
