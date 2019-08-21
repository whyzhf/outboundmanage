package com.along.outboundmanage.model.ExceptionEntity;

/**
   * 功能描述 
   * @author why
   * @date 2019/4/24
   * @description  响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static  <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    //自定义返回code，ResultCode类中枚举
    public static Result setFailResult(Integer code, String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
       // return new Result().setFailResult(code,message);

    }

    //自定义返回code，ResultCode类中枚举
    public static <T> Result<T> setCustomResult(Integer code, String message,T data) {
        return new Result()
                .setCode(code)
                .setMessage(message)
                .setData(data);
        //return new Result().setCustomResult( code,  message, data);

    }
    //自定义
    public static Result setCustomResult(Integer code, String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
//        return new Result().setCustomResult(code,message);
    }


}
