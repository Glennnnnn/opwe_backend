package com.ljl.opweAuthService.dao;

import com.ljl.opweAuthService.entity.pos.UserPo;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:18
 * @PackageName com.ljl.opweAuthService.dao
 * @ClassName UserMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserMapper {
    UserPo queryUserByUsername(String username);

    List<String> queryUserRoleByUsername(String username);
}
