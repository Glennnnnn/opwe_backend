package com.ljl.opweAuthService.utils;

import java.util.Base64;

/**
 * @Author Liu Jialin
 * @Date 2024/5/22 21:59
 * @PackageName com.ljl.opweAuthService.utils
 * @ClassName Base64Utils
 * @Description TODO
 * @Version 1.0.0
 */
public class Base64Utils {
    public static String base64Encode(String originalString) {
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes());
        return new String(encodedBytes);
    }

    public static String base64Decode(String originalString) {
        byte[] encodedBytes = Base64.getDecoder().decode(originalString.getBytes());
        return new String(encodedBytes);
    }
}
