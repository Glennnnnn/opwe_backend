package com.ljl.opweAuthService.service.impl;

import com.ljl.opweAuthService.dao.UserAuthMapper;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import com.ljl.opweAuthService.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserAuthMapper userAuthMapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public AuthUserPo queryAuthUserByUsername(String username) {
//        UserPo user = userMapper.queryUserByUsername(username).size() > 0 ? userMapper.queryUserByUsername(username).get(0) : null;
//        if (Objects.nonNull(user)){
//            //2.get password and encrypt it
//            String enPassword = passwordEncoder.encode(userPoList.get(0).getPassword());
//            //System.out.println(enPassword);
//            List<String> roleList = menuAuthMapper.checkUserRoleByUserId(String.valueOf(userPoList.get(0).getUserId()));
//            List<SimpleGrantedAuthority> authorities = roleList.stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add ROLE_ prefix to each role
//                    .collect(Collectors.toList());
//            return new LoginUserPo(userPoList.get(0), null);
//        }
        return null;
    }
}
