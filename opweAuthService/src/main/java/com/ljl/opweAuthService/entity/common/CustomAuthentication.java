package com.ljl.opweAuthService.entity.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author Liu Jialin
 * @Date 2024/10/24 19:27
 * @PackageName com.ljl.opweAuthService.entity.common
 * @ClassName CustomAuthentication
 * @Description TODO
 * @Version 1.0.0
 */
public class CustomAuthentication implements Authentication{
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated;

    // Constructors, getters and setters

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null; // Return credentials if available
    }

    @Override
    public Object getDetails() {
        return null; // Return details if available
    }

    @Override
    public Object getPrincipal() {
        return username; // Return the principal
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }
}
