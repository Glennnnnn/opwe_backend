package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.dtos.FileDto;
import com.ljl.opweOpenService.entity.pos.FilePo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Liu Jialin
 * @Date 2024/9/18 16:32
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName FileMapper
 * @Description TODO
 * @Version 1.0.0
 */
@Mapper
public interface FileMapper {
    void insertNewFile(FilePo filePo);

    FilePo queryFileById(Long fileId);
}
