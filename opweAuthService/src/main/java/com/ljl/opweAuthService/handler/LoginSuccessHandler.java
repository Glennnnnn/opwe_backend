package com.ljl.opweAuthService.handler;

import com.ljl.opweAuthService.entity.common.Result;
import com.ljl.opweAuthService.entity.constants.SecurityConstants;
import com.ljl.opweAuthService.utils.Base64Utils;
import com.ljl.opweAuthService.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author Liu Jialin
 * @Date 2024/4/23 13:56
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName LoginSuccessHandler
 * @Description TODO
 * @Version 1.0.0
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Result<String> success = Result.success();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(SecurityConstants.NONCE_HEADER_NAME, request.getSession().getId());
        response.getWriter().write(JsonUtils.objectCovertToJson(success));
        response.getWriter().flush();
    }
}
