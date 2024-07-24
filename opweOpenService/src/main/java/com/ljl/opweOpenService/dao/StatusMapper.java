package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.StatusPo;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 18:51
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName StatusMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface StatusMapper {
    List<StatusPo> queryStatusByGroup(String statusGroup);
}
