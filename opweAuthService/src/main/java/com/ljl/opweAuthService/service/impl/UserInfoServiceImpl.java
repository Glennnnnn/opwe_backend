package com.ljl.opweAuthService.service.impl;

import com.ljl.opweAuthService.dao.UserMapper;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import com.ljl.opweAuthService.entity.pos.UserPo;
import com.ljl.opweAuthService.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:15
 * @PackageName com.ljl.opweAuthService.service.impl
 * @ClassName USerInfoServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserMapper userMapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public AuthUserPo queryAuthUserByUsername(String username) {
        UserPo user = userMapper.queryUserByUsername(username);
        if (Objects.nonNull(user)){
            AuthUserPo authUserPo = new AuthUserPo();
            authUserPo.setUsername(username);

            authUserPo.setPassword(user.getUserPassword());
            //查询该用户的角色
            List<String> userRoles = userMapper.queryUserRoleByUsername(username);
            String[] a={};
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(userRoles.toArray(a));
            authUserPo.setAuthorities(authorityList);
            return authUserPo;
        }
        return null;
    }
}
