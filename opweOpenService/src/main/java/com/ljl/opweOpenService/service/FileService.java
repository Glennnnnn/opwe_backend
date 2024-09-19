package com.ljl.opweOpenService.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/9/19 17:53
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName FileService
 * @Description TODO
 * @Version 1.0.0
 */
public interface FileService {
    String uploadSingleFile(List<MultipartFile> fileList, String uploadFileName);

    String uploadMultipleFile(List<MultipartFile> fileList, String uploadFileName);
}
