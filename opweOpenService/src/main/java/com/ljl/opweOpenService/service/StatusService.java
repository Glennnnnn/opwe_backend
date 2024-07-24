package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.pos.StatusPo;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 19:05
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName StatusService
 * @Description TODO
 * @Version 1.0.0
 */
public interface StatusService {
    List<StatusPo> queryStatusByGroup(String statusGroup);
}
