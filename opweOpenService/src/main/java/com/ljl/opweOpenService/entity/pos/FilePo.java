package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

/**
 * @Author Liu Jialin
 * @Date 2024/9/18 16:29
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName FilePo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePo {
    private Long fileId;
    private String fileName;
    private String fileDesc;
    private Long fileCategory;
    private String fileLocation;
    private Date fileCreateTime;
    private Date fileUpdateTime;
    private Long fileCreateBy;
    private Long fileUpdateBy;
    private Integer fileLevel;
    private Long fileOwner;

}
