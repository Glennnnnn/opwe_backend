package com.ljl.opweGatewayService.entity.common;

/**
 * @Author Liu Jialin
 * @Date 2024/4/3 22:11
 * @PackageName com.ljl.opweGatewayService.entity.common
 * @ClassName HttpResponseCode
 * @Description TODO
 * @Version 1.0.0
 */
public enum HttpResponseCodeMsgEnum {
    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),

    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),

    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),

    NO_PERMISSION(1005,"无权限访问！"),
    UNAUTHORIZED(401, "系统错误"),

    INVALID_TOKEN(1004,"无效的token");



    private final int code;

    private final String msg;

    private HttpResponseCodeMsgEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
