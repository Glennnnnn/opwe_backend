package com.ljl.opweAuthService.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 21:04
 * @PackageName com.ljl.inventory.utils
 * @ClassName WebUtils
 * @Description TODO
 * @Version 1.0.0
 */
public class WebUtils {
    public static String renderString(HttpServletResponse response, String msg, int status) {
        try {
            response.setStatus(status);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
