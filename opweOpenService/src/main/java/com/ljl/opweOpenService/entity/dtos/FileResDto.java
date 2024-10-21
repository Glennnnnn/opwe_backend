package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Liu Jialin
 * @Date 2024/9/24 16:54
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName FileResDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResDto {
    private Long fileId;
    private String fileName;
    private String fileDesc;
    private Long fileCategory;
    private String fileLocation;
    private String fileUrl;
    private Date fileCreateTime;
    private Date fileUpdateTime;
    private Long fileCreateBy;
    private Long fileUpdateBy;
    private Integer fileLevel;
    private Long fileOwner;
    private String fileBucket;
}
