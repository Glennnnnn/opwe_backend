package com.ljl.opweUserService.manager;

/**
 * @Author Liu Jialin
 * @Date 2024/11/18 16:57
 * @PackageName com.ljl.opweUserService.manager
 * @ClassName ThreadLocalManager
 * @Description TODO
 * @Version 1.0.0
 */
public class FeignThreadLocalManager {

    // ThreadLocal variable to store data specific to each thread
    private static ThreadLocal<String> authorizationHeader = new ThreadLocal<>();
//    private static ThreadLocal<String> authToken = new ThreadLocal<>();
    // Set a value in ThreadLocal
    public static void setAuthorizationHeader(String value) {
        authorizationHeader.set(value);
    }

    public static String getAuthorizationHeader() {
        return authorizationHeader.get();
    }

    public static void clear() {
        authorizationHeader.remove();
//        authToken.remove();
    }
}
