package com.ljl.opweAuthService.handler;

import com.ljl.opweAuthService.entity.common.Result;
import com.ljl.opweAuthService.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author Liu Jialin
 * @Date 2024/4/23 14:26
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName LoginFailureHandler
 * @Description TODO
 * @Version 1.0.0
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 登录失败，写回401与具体的异常
        Result<String> error = Result.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.objectCovertToJson(error));
        response.getWriter().flush();
    }

}
