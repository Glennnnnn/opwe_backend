package com.ljl.opweUserService.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @Author Liu Jialin
 * @Date 2024/10/26 20:37
 * @PackageName com.ljl.opweUserService.handler
 * @ClassName CustomAccessDeniedHandler
 * @Description TODO
 * @Version 1.0.0
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"error\": \"ACCESS_DENIED\", \"message\": "  + accessDeniedException.getMessage() + "}");
        response.getWriter().flush();
    }
}
