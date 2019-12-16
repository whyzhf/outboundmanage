package com.along.outboundmanage.config;

import com.along.outboundmanage.model.ExceptionEntity.UnLoginException;
import com.along.outboundmanage.model.OutboundSession;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author why
 * @date 2019-04-05 10:52
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取session
        OutboundSession outboundSession = (OutboundSession)request.getSession().getAttribute("user");

        if (outboundSession == null){

	        throw  new UnLoginException();


        }else {
            return true;
        }

    }
}
