package com.ljl.opweAuthService.entity.pos;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:11
 * @PackageName com.ljl.opweAuthService.entity.pos
 * @ClassName AuthUserPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserPo implements UserDetails, Serializable {

    private UserPo userPo;

    private List<String> permissions;

    //both roles and authorities (permissions) are represented as GrantedAuthority objects
    //which are typically stored in a list like List<SimpleGrantedAuthority>.
    //The key difference is how roles are distinguished from authorities.
    //Roles are usually prefixed with "ROLE_" to differentiate them from other types of authorities.
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public AuthUserPo(UserPo userPo, List<String> permissions) {
        this.userPo = userPo;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //封装string类型信息
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPo.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userPo.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}