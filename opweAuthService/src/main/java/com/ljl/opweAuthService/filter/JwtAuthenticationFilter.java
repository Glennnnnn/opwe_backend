package com.ljl.opweAuthService.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import com.ljl.opweAuthService.utils.JwtUtils;
import com.ljl.opweAuthService.utils.RedisUtils;
import com.ljl.opweAuthService.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author Liu Jialin
 * @Date 2023/9/2 17:00
 * @PackageName com.ljl.inventory.filter
 * @ClassName JwtAuthenticationFilter
 * @Description TODO
 * @Version 1.0.0
 */
// Comment out for no jwt check
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private RedisUtils redisUtils;


    @Autowired
    public JwtAuthenticationFilter(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //fetch token
        String userId = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        //resolve token
        try {
            String token = authorizationHeader.substring(7);
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (ExpiredJwtException expiredJwtException) {
            //redisUtils.deleteObject("token:"+userId);
            ResponseResultPo responseResultPo = new ResponseResultPo();
            responseResultPo.setCode(HttpStatus.UNAUTHORIZED.value());
            responseResultPo.setMsg("Token expired");
            String json = JSON.toJSONString(responseResultPo);
            WebUtils.renderString(response, json, HttpStatus.UNAUTHORIZED.value());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("illegal token!");
        }
        //retrieve user info from redis
        String redisKey = "token:" + userId;
        AuthUserPo loginUserPo = redisUtils.getCacheObject(redisKey);
//        if(Objects.isNull(loginUserPo)){
//            throw new RuntimeException("User not exist");
//        }
        //store info in
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserPo, null, loginUserPo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

