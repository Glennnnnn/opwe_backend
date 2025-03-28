package com.ljl.opweAuthService.entity.constants;

/**
 * @Author Liu Jialin
 * @Date 2024/4/23 14:31
 * @PackageName com.ljl.opweAuthService.entity.constants
 * @ClassName RedisConstants
 * @Description TODO
 * @Version 1.0.0
 */
public class RedisConstants {
    /**
     * 认证信息存储前缀
     */
    public static final String SECURITY_CONTEXT_PREFIX_KEY = "security_context:";

    /**
     * 短信验证码前缀
     */
    public static final String SMS_CAPTCHA_PREFIX_KEY = "mobile_phone:";

    /**
     * 图形验证码前缀
     */
    public static final String IMAGE_CAPTCHA_PREFIX_KEY = "image_captcha:";

    /**
     * 默认过期时间，默认五分钟
     */
    public static final long DEFAULT_TIMEOUT_SECONDS = 60L * 5;
}
