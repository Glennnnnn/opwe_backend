package com.ljl.opweUserService.service;

import com.ljl.opweUserService.dao.UserMapper;
import com.ljl.opweUserService.entity.pos.UserPo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 21:25
 * @PackageName com.ljl.opweUserService.service
 * @ClassName UserService
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserService {
    int insertUser(UserPo userPo);

    Float confirmUserOrder(Long userId, Float orderAmount);
}
