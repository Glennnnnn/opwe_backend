package com.ljl.opweOpenService.controller;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.dtos.FileDto;
import com.ljl.opweOpenService.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/newFile")
    public ResponseResultPo insertNewFile(@RequestPart("fileData")FileDto fileDto, @RequestPart("files") List<MultipartFile> productImgs){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            if(productImgs.size() > 1){
                fileService.uploadMultipleFile(productImgs, fileDto.getUploadFileName());
            }else if(productImgs.size() == 1 ){
                fileService.uploadSingleFile(productImgs, fileDto.getUploadFileName());
            }else{
                throw new RuntimeException("no files uploaded");
            }
//            responseResultPo.setCode(HttpStatus.OK.value());
//            responseResultPo.setMsg(port);
//            responseResultPo.setData(productService.getProductWithImg(productId));
        } catch (Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("unknown failed");
        }
        return responseResultPo;

    }
}
