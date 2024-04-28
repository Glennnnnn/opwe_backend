package com.ljl.opweAuthService.service;

import com.ljl.opweAuthService.entity.pos.AuthUserPo;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:11
 * @PackageName com.ljl.opweAuthService.service
 * @ClassName UserInfoService
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserInfoService {
    AuthUserPo queryAuthUserByUsername(String username);
}
