package com.along.outboundmanage.config;

//import  com.along.outboundmanage.model.LoginInfo;
import com.along.outboundmanage.model.OutboundSession;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author why
 * @date 2019-04-05 10:52
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

      /*  response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
       // response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type, Accept-Language, Origin, Accept-Encoding");*/
        //获取session
        OutboundSession outboundSession = (OutboundSession)request.getSession().getAttribute("user");
       // System.out.println("outboundSession:"+outboundSession);
        if (outboundSession == null){
            response.sendRedirect("/login");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('/login','_top')");
            out.println("</script>");
            out.println("</html>");
            return false;
        }else {
            return true;
        }
      //  return true;
    }
}
