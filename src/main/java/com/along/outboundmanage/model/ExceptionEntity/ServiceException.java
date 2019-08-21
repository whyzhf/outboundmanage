package com.along.outboundmanage.model.ExceptionEntity;

/**
   * 功能描述 
   * @author why
   * @date 2019/4/24
   * @return
   * @description  服务（业务）异常如“ 账号或密码错误 ”，
   * 该异常只做INFO级别的日志记录 @see WebMvcConfigurer
   * throw new ServiceException("用户名密码错误");
 */

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
