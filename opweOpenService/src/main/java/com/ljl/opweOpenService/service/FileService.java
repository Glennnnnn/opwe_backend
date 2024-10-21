package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.dtos.FileDto;
import com.ljl.opweOpenService.entity.dtos.FileResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author Liu Jialin
 * @Date 2024/9/19 17:53
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName FileService
 * @Description TODO
 * @Version 1.0.0
 */
public interface FileService {
    Map<String, String> uploadSingleFile(List<MultipartFile> fileList, String uploadFileName);

    Map<String, String> uploadMultipleFile(List<MultipartFile> fileList, String uploadFileName);

    Long createNewFileInDB(FileDto fileDto, String fileLocation);

    FileResDto queryFileById(Long fileId);
}
