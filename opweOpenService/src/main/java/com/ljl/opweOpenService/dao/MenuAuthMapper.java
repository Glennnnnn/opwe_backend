package com.ljl.opweOpenService.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2023/9/4 20:29
 * @PackageName com.ljl.inventory.dao
 * @ClassName MenuAuthMapper
 * @Description TODO
 * @Version 1.0.0
 */
@Mapper
public interface MenuAuthMapper {
    public List<String> checkUserAuthByUserId(String userId);
}
