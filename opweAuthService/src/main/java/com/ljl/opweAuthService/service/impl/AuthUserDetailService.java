package com.ljl.opweAuthService.service.impl;

import com.ljl.opweAuthService.dao.UserAuthMapper;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import com.ljl.opweAuthService.entity.pos.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    UserAuthMapper userAuthMapper;
//    @Autowired
//    MenuAuthMapper menuAuthMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserPo userPo = userAuthMapper.checkUserByName(username).size() > 0 ? userAuthMapper.checkUserByName(username).get(0) : null;
        if (!Objects.nonNull(userPo)) {
            throw new UsernameNotFoundException("Username not found!");
        } else {
            //2.get password and encrypt it
            String enPassword = passwordEncoder.encode(userPo.getUserPassword());
            System.out.println(enPassword);
            //查询该用户的角色
            List<String> userRoles = userAuthMapper.queryUserRoleByUsername(username);
            List<SimpleGrantedAuthority> roleList = userRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
//            AuthUserPo authUserPo = new AuthUserPo();
//            authUserPo.setPassword(enPassword);
//            authUserPo.setAuthorities(authorityList);
            AuthUserPo authUserPo = new AuthUserPo(userPo, null);
            authUserPo.setAuthorities(roleList);
            authUserPo.setPermissions(userRoles);
            return authUserPo;
        }
    }
}
