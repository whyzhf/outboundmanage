package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.CustomException;
import com.along.outboundmanage.model.OutboundSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
   *页面跳转控制器
   * @author why
   * @date 2019/4/29
   * @return
   * @description  ${description}
 */

@RestController
@RequestMapping("/index")
public class IndexController {


    /**
     * 首页
     * @param modelAndView
     * @param role_id
     * @return
     */
    @RequestMapping(value="")
    public ModelAndView getAllpar(ModelAndView modelAndView, HttpServletRequest request, String role_id){
        HttpSession session = request.getSession();
        OutboundSession outboundSession = (OutboundSession)session.getAttribute("user");
        if((role_id == null)&&session.getAttribute("user")!=null) {
            int roleId = outboundSession.getRoleId();

        }

        modelAndView.addObject("roleId",role_id);
        modelAndView.setViewName("index");
        return  modelAndView;
    }



    @RequestMapping(value = "/index")
    public String getIndex() {

        return "index";
    }


    @RequestMapping("/manage/{pagename}")
    public ModelAndView review(@PathVariable String pagename, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String vname = "manage/" + pagename;
        mv.setViewName(vname);
        return mv;
    }
    @RequestMapping("/views/{pagename}")
    public ModelAndView views(@PathVariable String pagename, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();
        String vname = "views/" + pagename;
        mv.setViewName(vname);
        return mv;
    }
}
