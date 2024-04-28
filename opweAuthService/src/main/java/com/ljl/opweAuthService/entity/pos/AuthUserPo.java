package com.ljl.opweAuthService.entity.pos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

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
    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUserPo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
