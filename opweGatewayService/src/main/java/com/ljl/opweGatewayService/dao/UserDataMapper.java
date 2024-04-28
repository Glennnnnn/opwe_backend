package com.ljl.opweGatewayService.dao;

import com.ljl.opweGatewayService.entity.pos.UserPo;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/4/2 20:23
 * @PackageName opweGatewayService.dao
 * @ClassName UserDataMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserDataMapper {
    List<UserPo> checkUserByName(String username);
}
