package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.dao.FileMapper;
import com.ljl.opweOpenService.entity.dtos.FileDto;
import com.ljl.opweOpenService.entity.dtos.FileResDto;
import com.ljl.opweOpenService.entity.pos.FilePo;
import com.ljl.opweOpenService.service.FileService;
import com.ljl.opweOpenService.service.MinioService;
import com.ljl.opweOpenService.utils.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private FileMapper fileMapper;

    private SnowflakeUtil snowflakeUtil;

    private static final String TEMP_BUCKET_NAME = "temp-bucket";

    private static final String FILE_BUCKET_NAME = "video-bucket";

    @Autowired
    public FileServiceImpl(MinioService minioService, FileMapper fileMapper, SnowflakeUtil snowflakeUtil) {
        this.minioService = minioService;
        this.fileMapper = fileMapper;
        this.snowflakeUtil = snowflakeUtil;
    }

    @Override
    public Map<String, String> uploadSingleFile(List<MultipartFile> fileList, String uploadFileName) {
        String objectKey = uploadFileName;
        Map<String, String> resultMap = new HashMap<>();
        if(checkFileBeforeUpload("File" + uploadFileName)){
            throw new RuntimeException("File exist!");
        }
        try {
            minioService.uploadFileSimple(TEMP_BUCKET_NAME, uploadFileName, fileList.get(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resultMap.put("url", minioService.generatePresignedUrl(FILE_BUCKET_NAME, "File" + objectKey, 600));
        resultMap.put("name", objectKey);
        return resultMap;
    }

    @Override
    public Map<String, String> uploadMultipleFile(List<MultipartFile> fileList, String uploadFileName) {
        String objectKey;
        Map<String, String> resultMap = new HashMap<>();
        if(checkFileBeforeUpload("File" + uploadFileName)){
            throw new RuntimeException("File exist!");
        }
        for (MultipartFile file : fileList) {
            minioService.uploadFileSimple(TEMP_BUCKET_NAME, file.getOriginalFilename(), file);
        }
        List<String> fileNameList = minioService.fuzzyListObjects(TEMP_BUCKET_NAME, uploadFileName);
        try {
            objectKey = minioService.composeObject(fileNameList, "File" + uploadFileName, TEMP_BUCKET_NAME, FILE_BUCKET_NAME);
            minioService.deleteObjects(fileNameList, TEMP_BUCKET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resultMap.put("url",  minioService.generatePresignedUrl(FILE_BUCKET_NAME, "File" + objectKey, 600));
        resultMap.put("name", objectKey);
        return resultMap;
    }

    public boolean checkFileBeforeUpload(String uploadFileName){
        return minioService.isFileExists(FILE_BUCKET_NAME, uploadFileName);
    }

    @Override
    public Long createNewFileInDB(FileDto fileDto, String fileLocation){
        Date currentDate = new Date();
        Long fileId = snowflakeUtil.getNextId();
        FilePo filePo = new FilePo(
                fileId,
                fileDto.getFileName(),
                fileDto.getFileDesc(),
                null,
                fileLocation,
                currentDate,
                currentDate,
                null,
                null,
                1,
                null,
                TEMP_BUCKET_NAME
        );
        fileMapper.insertNewFile(filePo);
        return fileId;
    }

    public FileResDto queryFileById(Long fileId){
        FilePo filePo = fileMapper.queryFileById(fileId);
        String url;
        FileResDto fileResDto = new FileResDto(
                filePo.getFileId(),
                filePo.getFileName(),
                filePo.getFileDesc(),
                filePo.getFileCategory(),
                filePo.getFileLocation(),
                null,
                filePo.getFileCreateTime(),
                filePo.getFileUpdateTime(),
                filePo.getFileCreateBy(),
                filePo.getFileUpdateBy(),
                filePo.getFileLevel(),
                filePo.getFileOwner(),
                filePo.getFileBucket()
        );
        try{
            url = minioService.generatePresignedUrl(fileResDto.getFileBucket(), filePo.getFileLocation(), 600);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        fileResDto.setFileUrl(url);
        return fileResDto;
    }
}
