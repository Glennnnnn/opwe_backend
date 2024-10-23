package com.ljl.opweAuthService.service;

import com.ljl.opweAuthService.entity.pos.UserPo;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2023/8/12 19:11
 * @PackageName com.ljl.inventory.service.Impl
 * @ClassName UserService
 * @Description TODO
 * @Version 1.0.0
 */

public interface UserService {
    public List<UserPo> checkUserByName(String username);

    public List<UserPo> queryBasicUserInfoByName(String username);

    public List<UserPo> queryBasicUserInfoById(Long userId);

    public List<UserPo> queryBasicUserInfoByToken(String token) throws Exception;

    public boolean checkUserByToken(String authorization) throws Exception;
}
