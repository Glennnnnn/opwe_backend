package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.StatusPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 18:51
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName StatusMapper
 * @Description TODO
 * @Version 1.0.0
 */
@Mapper
public interface StatusMapper {
    List<StatusPo> queryStatusByGroup(String statusGroup);
    Long queryStatusByGroupAndName(@Param("statusGroup") String statusGroup, @Param("statusName") String statusName);
}
