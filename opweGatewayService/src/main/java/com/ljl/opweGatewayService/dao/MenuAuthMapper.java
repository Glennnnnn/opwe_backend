package com.ljl.opweGatewayService.dao;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/4/2 20:28
 * @PackageName com.ljl.opweGatewayService.dao
 * @ClassName MenuAuthMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface MenuAuthMapper {
    List<String> checkUserAuthByUserId(String userId);
}
