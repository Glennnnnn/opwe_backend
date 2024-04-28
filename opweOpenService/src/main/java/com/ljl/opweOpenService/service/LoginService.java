package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.pos.UserPo;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 23:25
 * @PackageName com.ljl.inventory.service.Impl
 * @ClassName LoginService
 * @Description TODO
 * @Version 1.0.0
 */
public interface LoginService {
    ResponseResultPo login(UserPo userPo);

    ResponseResultPo logout();
}
