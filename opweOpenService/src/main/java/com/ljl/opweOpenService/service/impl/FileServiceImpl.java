package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.service.FileService;
import com.ljl.opweOpenService.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/9/19 17:53
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class FileServiceImpl implements FileService {

    private MinioService minioService;

    private static final String BUCKET_NAME = "tempBucket";

    @Autowired
    public FileServiceImpl(MinioService minioService) {
        this.minioService = minioService;
    }

    @Override
    public String uploadSingleFile(List<MultipartFile> fileList, String uploadFileName) {
        for (MultipartFile file : fileList) {
            minioService.uploadFileSimple(BUCKET_NAME, file.getName(), file);
        }
        List<String> fileNameList = minioService.fuzzyListObjects(BUCKET_NAME, uploadFileName);
        minioService.composeObject(fileNameList, uploadFileName, BUCKET_NAME);
        minioService.deleteObjects(fileNameList, BUCKET_NAME);
        return null;
    }

    @Override
    public String uploadMultipleFile(List<MultipartFile> fileList, String uploadFileName) {
        return null;
    }


}
