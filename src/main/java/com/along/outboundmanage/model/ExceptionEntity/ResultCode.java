package com.along.outboundmanage.model.ExceptionEntity;

/**
   * 响应码枚举
   * @author why
   * @date 2019/4/24
   * @description  响应码枚举，参考HTTP状态码的语义
 */

public enum ResultCode {
    //成功
    SUCCESS(200,"请求成功"),
    LOGIN_FILE(4000,"用户名或密码错误"),
    //失败
    FAIL(400,"请求失败"),
    //失败
    PAGE_FOUND(1504,"页面请求失败，该页面不存在或页面非法访问"),
    //接口不存在
    NOT_FOUND(1404,"接口不存在"),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500,"服务器内部错误");

    private final Integer code;
    private final String message;
    ResultCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }
    public String message() {
        return message;
    }
}
