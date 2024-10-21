package com.ljl.opweOpenService.controller;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.dtos.FileDto;
import com.ljl.opweOpenService.entity.dtos.FileResDto;
import com.ljl.opweOpenService.service.FileService;
import com.ljl.opweOpenService.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author Liu Jialin
 * @Date 2024/9/18 16:40
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName FileController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/fileService")
public class FileController {
    private FileService fileService;

    private MinioService minioService;

    @Autowired
    public FileController(FileService fileService, MinioService minioService){
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping("/newFile")
    public ResponseResultPo insertNewFile(@RequestPart("fileData")FileDto fileDto, @RequestPart("files") List<MultipartFile> productImgs){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        Map<String, String> result;
        try{
            if(productImgs.size() > 1){
                result = fileService.uploadMultipleFile(productImgs, fileDto.getUploadFileName());
            }else if(productImgs.size() == 1 ){
                result = fileService.uploadSingleFile(productImgs, fileDto.getUploadFileName());
            }else{
                throw new RuntimeException("no files uploaded");
            }
            Long fileId = fileService.createNewFileInDB(fileDto, result.get("name"));
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("SUCCESS");
            responseResultPo.setData(fileId);
        } catch (Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg(e.getMessage());
        }
        return responseResultPo;
    }

    @GetMapping("/fileById")
    public ResponseResultPo queryFileById(@RequestParam("fileId")Long fileId){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            FileResDto fileResDto = fileService.queryFileById(fileId);
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("SUCCESS");
            responseResultPo.setData(fileResDto);
        } catch (Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg(e.getMessage());
        }
        return responseResultPo;
    }


}
