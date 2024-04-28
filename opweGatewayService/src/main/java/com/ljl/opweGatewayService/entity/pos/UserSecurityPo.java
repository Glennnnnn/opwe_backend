package com.ljl.opweGatewayService.entity.pos;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 15:56
 * @PackageName com.ljl.inventory.entity
 * @ClassName LoginUserPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
public class UserSecurityPo implements UserDetails {

    private UserPo userPo;

    private List<String> permissions;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public UserSecurityPo(UserPo userPo, List<String> permissions) {
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


