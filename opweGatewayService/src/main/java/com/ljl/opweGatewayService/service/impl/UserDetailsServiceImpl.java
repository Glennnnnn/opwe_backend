package com.ljl.opweGatewayService.service.impl;

import com.ljl.opweGatewayService.dao.MenuAuthMapper;
import com.ljl.opweGatewayService.dao.UserDataMapper;
import com.ljl.opweGatewayService.entity.pos.UserPo;
import com.ljl.opweGatewayService.entity.pos.UserSecurityPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/4/1 21:49
 * @PackageName opweGatewayService.service.impl
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    UserDataMapper userDataMapper;
//
    @Autowired
    MenuAuthMapper menuAuthMapper;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // TODO call another service to get user list
        List<UserPo> userPoList = userDataMapper.checkUserByName(username);
        if (userPoList.size() == 0) {
            throw new UsernameNotFoundException("Username not found!");
        } else {
            //2.get password and encrypt it
//          String enPassword = passwordEncoder.encode(userPoList.get(0).getUserPassword());
//          System.out.println(enPassword);
            List<String> permissionList = new ArrayList<>();
            // TODO call another service to get permission list
            permissionList = menuAuthMapper.checkUserAuthByUserId(String.valueOf(userPoList.get(0).getUserId()));
            UserSecurityPo userSecurityPo = new UserSecurityPo(userPoList.get(0), permissionList);
            return Mono.just(userSecurityPo);
        }
    }
}
