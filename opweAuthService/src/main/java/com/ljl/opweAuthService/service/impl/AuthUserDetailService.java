package com.ljl.opweAuthService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:10
 * @PackageName com.ljl.opweAuthService.service.impl
 * @ClassName AuthUserDetailService
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class AuthUserDetailService implements UserDetailsService {
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username){
        UserDetails userDetails = userInfoService.queryAuthUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return userDetails;
        }
    }
}
