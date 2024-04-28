package com.ljl.opweAuthService.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Liu Jialin
 * @Date 2024/4/25 22:02
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName BasicTargetAuthenticationEntryPoint
 * @Description TODO
 * @Version 1.0.0
 */
public class BasicTargetAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter printWriter = new PrintWriter(response.getOutputStream());
        printWriter.write("Http Status 401: " + authException.getLocalizedMessage());
    }
}
