package com.ljl.opweAuthService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ljl.opweAuthService.dao.UserAuthMapper;
import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import com.ljl.opweAuthService.entity.dtos.LoginResponseDto;
import com.ljl.opweAuthService.entity.dtos.LoginUserResDto;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import com.ljl.opweAuthService.entity.pos.UserPo;
import com.ljl.opweAuthService.service.LoginService;
import com.ljl.opweAuthService.utils.JwtUtils;
import com.ljl.opweAuthService.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 16:06
 * @PackageName com.ljl.inventory.service.Impl
 * @ClassName LoginServiceImpl
 * @Description verify the user --- Part of the security framework component:
 * @Version 1.0.0
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserAuthMapper userAuthMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public LoginResponseDto login(UserPo userPo) {
        // authenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPo.getUserName(), userPo.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("Incorrect username or password!");
        }
        // 认证成功,生成jwt
        AuthUserPo loginUserPo = (AuthUserPo) authenticate.getPrincipal();
        JSONObject jsonObject = new JSONObject();
        String userPoId = String.valueOf(loginUserPo.getUserPo().getUserId());
        jsonObject.put("userData", loginUserPo);
        String jwtToken = JwtUtils.createJWT(userPoId);

        // 存入redis userid作为key
        redisUtils.setCacheObject("token:" + userPoId, loginUserPo);
        // 返回结果
//        HashMap<String, String> resultJson = new HashMap<>();
//        resultJson.put("token", jwtToken);
        LoginUserResDto loginUserResDto = new LoginUserResDto(
                loginUserPo.getUserPo().getUserId(),
                loginUserPo.getUserPo().getUserName(),
                loginUserPo.getUserPo().getUserEmail(),
                loginUserPo.getUserPo().getUserPhone(),
                loginUserPo.getUserPo().getUserRoleName()
        );
        LoginResponseDto result = new LoginResponseDto(loginUserResDto, jwtToken);
        return result;
    }

    @Override
    public ResponseResultPo logout() {
        //get user info from securityContestHolder
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUserPo loginUserPo = (AuthUserPo) authenticationToken.getPrincipal();
        String userId = String.valueOf(loginUserPo.getUserPo().getUserId());
        //delete from redis
        redisUtils.deleteObject("token:" + userId);
        return new ResponseResultPo(HttpStatus.OK.value(), "Logout success!", null);
    }
}
