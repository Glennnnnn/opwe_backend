package com.ljl.opweAuthService.entity.constants;

/**
 * @Author Liu Jialin
 * @Date 2024/4/23 14:31
 * @PackageName com.ljl.opweAuthService.entity.constants
 * @ClassName SecurityConstants
 * @Description TODO
 * @Version 1.0.0
 */
public class SecurityConstants {
    /**
     * 授权确认页面地址
     */
    public static final String DEVICE_ACTIVATED_URI = "http://127.0.0.1:5173/activated";

    /**
     * 授权确认页面地址
     */
    public static final String DEVICE_ACTIVATE_URI = "http://127.0.0.1:5173/activate";

    /**
     * 授权确认页面地址
     */
    public static final String CONSENT_PAGE_URI = "http://127.0.0.1:3000/consentPage";
//    public static final String CONSENT_PAGE_URI = "https://www.baidu.com";

    /**
     * 微信登录相关参数——openid：用户唯一id
     */
    public static final String WECHAT_PARAMETER_OPENID = "openid";

    /**
     * 微信登录相关参数——forcePopup：强制此次授权需要用户弹窗确认
     */
    public static final String WECHAT_PARAMETER_FORCE_POPUP = "forcePopup";

    /**
     * 微信登录相关参数——secret：微信的应用秘钥
     */
    public static final String WECHAT_PARAMETER_SECRET = "secret";

    /**
     * 微信登录相关参数——appid：微信的应用id
     */
    public static final String WECHAT_PARAMETER_APPID = "appid";

    /**
     * 三方登录类型——微信
     */
    public static final String THIRD_LOGIN_WECHAT = "wechat";

    /**
     * 三方登录类型——Gitee
     */
    public static final String THIRD_LOGIN_GITEE = "gitee";

    /**
     * 三方登录类型——Github
     */
    public static final String THIRD_LOGIN_GITHUB = "github";

    /**
     * 随机字符串请求头名字
     */
    public static final String NONCE_HEADER_NAME = "nonceid";

    /**
     * 登录方式入参名
     */
    public static final String LOGIN_TYPE_NAME = "loginType";

    /**
     * 验证码id入参名
     */
    public static final String CAPTCHA_ID_NAME = "captchaId";

    /**
     * 验证码值入参名
     */
    public static final String CAPTCHA_CODE_NAME = "code";

    /**
     * 登录方式——短信验证码
     */
    public static final String SMS_LOGIN_TYPE = "smsCaptcha";

    /**
     * 登录方式——账号密码登录
     */
    public static final String PASSWORD_LOGIN_TYPE = "passwordLogin";

    /**
     * 权限在token中的key
     */
    public static final String AUTHORITIES_KEY = "authorities";

    /**
     * 自定义 grant type —— 短信验证码
     */
    public static final String GRANT_TYPE_SMS_CODE = "urn:ietf:params:oauth:grant-type:sms_code";

    /**
     * 自定义 grant type —— 短信验证码 —— 手机号的key
     */
    public static final String OAUTH_PARAMETER_NAME_PHONE = "phone";

    /**
     * 自定义 grant type —— 短信验证码 —— 短信验证码的key
     */
    public static final String OAUTH_PARAMETER_NAME_SMS_CAPTCHA = "sms_captcha";
}
