package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/9/18 16:57
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName FileDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileName;
    private String fileDesc;
    private String uploadFileName;
}
