package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.dao.StatusMapper;
import com.ljl.opweOpenService.entity.enums.StatusGroupEnum;
import com.ljl.opweOpenService.entity.pos.StatusPo;
import com.ljl.opweOpenService.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 19:09
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName StatusServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class StatusServiceImpl implements StatusService {
    private final StatusMapper statusMapper;

    @Autowired
    public StatusServiceImpl(StatusMapper statusMapper){
        this.statusMapper = statusMapper;
    }

    @Override
    public List<StatusPo> queryStatusByGroup(String statusGroup) {
        switch (statusGroup){
            case "product":
                return statusMapper.queryStatusByGroup(StatusGroupEnum.ProductGroupStatus.getStatusGroup());
        }
        return null;
    }
}
