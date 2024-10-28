package com.ljl.opweAuthService.service;

import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import com.ljl.opweAuthService.entity.dtos.LoginResponseDto;
import com.ljl.opweAuthService.entity.pos.UserPo;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 23:25
 * @PackageName com.ljl.inventory.service.Impl
 * @ClassName LoginService
 * @Description TODO
 * @Version 1.0.0
 */
public interface LoginService {
    LoginResponseDto login(UserPo userPo);

    ResponseResultPo logout();
}
