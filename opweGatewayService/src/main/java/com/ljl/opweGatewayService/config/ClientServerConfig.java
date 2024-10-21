package com.ljl.opweGatewayService.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Liu Jialin
 * @Date 2024/4/22 22:28
 * @PackageName com.ljl.opweGatewayService.config
 * @ClassName ClientServerConfig
 * @Description TODO
 * @Version 1.0.0
 */

//@Configuration
public class ClientServerConfig {
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//            authorities.forEach(authority -> {
//                if (authority instanceof OAuth2UserAuthority oAuth2UserAuthority) {
//                    // 从认证服务获取的用户信息中提取权限信息
//                    Object userAuthorities = oAuth2UserAuthority.getAttributes().get("authorities");
//                    if (userAuthorities instanceof Collection<?> collection) {
//                        // 转为SimpleGrantedAuthority的实例并插入mappedAuthorities中
//                        collection.stream().filter(a -> a instanceof String)
//                                .map(String::valueOf)
//                                .map(SimpleGrantedAuthority::new)
//                                .forEach(mappedAuthorities::add);
//                    }
//                }
//            });
//
//            return mappedAuthorities;
//        };
//    }
}
