package com.ljl.opweAuthService.handler;

import com.ljl.opweAuthService.entity.constants.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author Liu Jialin
 * @Date 2024/4/25 22:16
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName CustomAuthenticationEntryPoint
 * @Description TODO
 * @Version 1.0.0
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 设置状态码为401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型为JSON
        response.setContentType("application/json");
        // 可以根据需要设置其他响应头
        StringBuffer requestUrl = request.getRequestURL();

        // response.setHeader("Custom-Header", "Value");
        //String targetUrl = SecurityConstants.NONCE_HEADER_NAME + "=" + request.getSession(Boolean.TRUE).getId();
        // 构建响应体，这里是一个简单的JSON格式
        //String responseBody = "{\"message\": \"Authentication failed\"" + targetUrl + "}";
        response.setHeader(SecurityConstants.NONCE_HEADER_NAME,request.getSession(Boolean.TRUE).getId());
        // 将响应内容写入响应输出流
        //response.getWriter().write(responseBody);
        response.getWriter().flush();
    }
}
