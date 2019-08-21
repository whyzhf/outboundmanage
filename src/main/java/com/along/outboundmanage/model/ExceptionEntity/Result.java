package com.along.outboundmanage.model.ExceptionEntity;

import com.alibaba.fastjson.JSON;

/**
 * @Auther: why
 * @Data:2019/4/19
 * @Deacription:统一返回类型
 */
public class Result<T> {

    private Integer code;
    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }
    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }
    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

  /*  public Result setFailResult(Integer code, String message){
        this.code = code;
        this.message = message;
        return this;
    }
    public Result setCustomResult(Integer code, String message){
        this.code = code;
        this.message = message;
        return this;
    }
    public  Result setCustomResult(Integer code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data=data;
        return this;

    }*/
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
