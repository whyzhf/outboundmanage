package com.along.outboundmanage.config;


import com.alibaba.fastjson.JSON;
import com.along.outboundmanage.model.ExceptionEntity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yeauty.standard.ServerEndpointExporter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author why
 * @date 2019-04-05 10:52
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
   //当前激活的配置文件
  /*  @Value("${spring.profiles.active}")
    private String env;*/

    @Bean
    MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }


    /**
     * 功能描述(打war包时需要注释掉，打成war或者传统方式发布到tomcat中， 相当于启动了两次 )
     * @author why
     * @date 2019/6/10
     * @param
     * @return org.springframework.web.socket.server.standard.ServerEndpointExporter
     * @description 配置ServerEndpointExporter，配置后会自动注册所有“@ServerEndpoint”注解声明的Websocket Endpoint
     */
   @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
   /* @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //设置项目默认页面
        registry.addViewController("/").setViewName("index");
    }*/

    /**
     * 配置静态访问资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/index","/login","/login/logVal");
    }

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                if (e instanceof ServiceException) {
                    //业务失败的异常，如“账号或密码错误”
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                    logger.info(e.getMessage());
                }else if (e instanceof UnLoginException) {
	                result.setCode(ResultCode.UNLOGIN).setMessage("请登录");
                }  else if (e instanceof NoHandlerFoundException) {
                    result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof CustomException) {
                    result.setCode(ResultCode.NOT_FOUND).setMessage("页面 [" + request.getRequestURI() + "] 不存在，页面请求失败，该页面不存在或页面非法访问");
                }else if (e instanceof ServletException) {
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                    logger.error(message, e);
                }

                responseResult(response, result);
               /* ModelAndView  modelAndView=new ModelAndView();
                 modelAndView.addObject("msg",result);
                // modelAndView.setViewName("error");
                 return  modelAndView;*/
               return new ModelAndView();
            }

        });
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        PrintWriter output= null;
        try {
            output= response.getWriter();
          //  output.write(JSON.toJSONString(result));
            output.print(JSON.toJSONString(result));
            output.flush();
            output.close();
           // response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
           // logger.error(ex.getMessage());
        }finally {
            //output.close();
        }
    }
    // 跨域
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        // 设置setAllowCredentials = true后就不能设置为*了，要设置具体的
        corsConfiguration.addAllowedOrigin("http://192.168.100.117:8080");
        corsConfiguration.addAllowedOrigin("http://192.168.100.119:8080");
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://localhost:8081");
        corsConfiguration.addAllowedOrigin("http://localhost:63342");
        corsConfiguration.addAllowedOrigin("http://120.77.252.208:8080");


        // 允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
