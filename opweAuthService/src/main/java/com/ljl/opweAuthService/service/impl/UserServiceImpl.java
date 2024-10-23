package com.ljl.opweAuthService.service.impl;

import com.ljl.opweAuthService.dao.UserAuthMapper;
import com.ljl.opweAuthService.entity.pos.UserPo;
import com.ljl.opweAuthService.service.UserService;
import com.ljl.opweAuthService.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2023/8/12 19:13
 * @PackageName com.ljl.inventory.service.Impl
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public List<UserPo> checkUserByName(String username) {
        List<UserPo> userPoList = userAuthMapper.checkUserByName(username);
        return userPoList;
    }

    @Override
    public List<UserPo> queryBasicUserInfoByName(String username) {
        List<UserPo> userPoList = userAuthMapper.queryBasicUserInfoByName(username);
        return userPoList;
    }

    @Override
    public List<UserPo> queryBasicUserInfoById(Long userId) {
        List<UserPo> userPoList = userAuthMapper.queryBasicUserInfoById(userId);
        return userPoList;
    }

    @Override
    public List<UserPo> queryBasicUserInfoByToken(String authorization) throws Exception {
        String token = authorization.substring(7);
        Long userId = Long.parseLong(JwtUtils.parseJWT(token).getSubject());
        log.info(String.valueOf(userId));
        List<UserPo> userPoList = userAuthMapper.queryBasicUserInfoById(userId);
        return userPoList;
    }

    @Override
    public boolean checkUserByToken(String authorization) throws Exception {
        String token = authorization.substring(7);
        Claims claims = JwtUtils.parseJWT(token);
//        if(claims.getExpiration() >= System.current){
//
//        }
        return false;
    }
}
