package com.along.outboundmanage.controller;
/**
 * @Description:
 * @Author: why
 * @Date: 2018-11-29 19:41
 */

import com.alibaba.fastjson.JSON;
import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultCode;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundRole;
import com.along.outboundmanage.model.OutboundSession;
import com.along.outboundmanage.model.OutboundUser;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.LoginService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.along.outboundmanage.utill.GeneralUtils.getJsonStr;


/**
   * 功能描述 
   * @author why
   * @date 2019/4/29
   * @return
   * @description  ${description}
 */


@Controller
@RequestMapping(value = "/login")
public class LoginController {

   @Resource
    private LoginService loginService;

    /**
     * 判断Session是否过期 过期则返回登录页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "")
    public String login(HttpServletRequest request) {
        String agent = request.getHeader("user-agent");
        HttpSession session = request.getSession();

       /* if (session != null ){
            //设置session
            session.setAttribute("user",new OutboundSession.Builder().userName("www").build() );
            return "login";
        }*/
        if (agent.contains("Windows NT") && session.getAttribute("user") == null) {
            return "login";
        } else if (session.getAttribute("user") != null) {
            return "index";
        } else {
            return "login";
        }
    }

    /**
     * 登陆
     * @param user
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logVal",produces = {"application/json;charset=UTF-8"})
    public Result logVal(@RequestBody OutboundUser user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map=new HashMap<>();
        HttpSession session = request.getSession();

	    OutboundSession outSession=(OutboundSession)session.getAttribute("user");
        if (outSession != null && user.getUserName().equals(outSession.getUserName())) {
	        map.put("url", "/index");
	     //   System.out.println(outSession);
	        return  ResultGenerator.genSuccessResult(session.getAttribute("user"));
        }else{
	        OutboundUser returnUser = loginService.checkLogin(user);
            if (returnUser != null ){
                //设置session
                session.setAttribute("user", loginService.getSession(returnUser.getId()));
                map.put("url", "/index");
            }else{
                return  ResultGenerator.setFailResult(ResultCode.LOGIN_FILE.code(),ResultCode.LOGIN_FILE.message());
            }
        }
        // System.out.println(session.getAttribute("user"));
        return ResultGenerator.genSuccessResult(session.getAttribute("user"));

    }
    /**
     * 用户清单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/userList")
    public Result userList(@RequestBody PubParam pubParam, HttpServletRequest request) {
      /*  HttpSession session = request.getSession();
        OutboundSession outboundSession= (OutboundSession) session.getAttribute("user");*/
        PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
        List<OutboundSession> userList = loginService.userList(pubParam.getAreaId());
        PageInfo<OutboundSession> pageInfo = new PageInfo<>(userList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

	/**
	 * 可选角色
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/roleList")
	public Result roleList(@RequestBody PubParam pubParam, HttpServletRequest request) {
		List<OutboundRole> roleList = new ArrayList<>();
     if(2==pubParam.getRoleId()){
	     roleList.add(new OutboundRole(2,"地市管理员"));
	     roleList.add(new OutboundRole(3,"地市普通用户"));
     }else if(4==pubParam.getRoleId()){
	     roleList.add(new OutboundRole(4,"监所管理员"));
	     roleList.add(new OutboundRole(5,"监所普通用户"));
     }

		return ResultGenerator.genSuccessResult(roleList);
	}
    /**
     * 用户新增
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody OutboundUser user,HttpServletRequest request) {
        Integer roleId=user.getRoleId();
        Map<String,Object> map=new HashMap<>();
        if(roleId==null||roleId==0){
            roleId=5;
        }else{
            user.setRoleId(null);
        }
        OutboundUser returnUser = loginService.addUser(user);
        if(returnUser==null){
            return ResultGenerator.setCustomResult(4000,"新增失败");
        }else{
            loginService.addUserRole(returnUser.getId(),roleId);
           // returnUser.setRoleId(roleId);
            OutboundSession resuser = loginService.getAllUserById(returnUser.getId());
            map.put("id",user.getId());
            map.put("userName",resuser.getUserName());
            map.put("password",user.getPassword());
            map.put("trueName",resuser.getTrueName());
            map.put("card",resuser.getCard());
            map.put("areaId",resuser.getAreaId());
            map.put("areaName",resuser.getAreaName());
            map.put("roleId",resuser.getRoleId());
            map.put("roleName",resuser.getRoleName());
            return ResultGenerator.genSuccessResult(map);
        }

    }
    /**
     * 修改用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/upUser")
    public Result upUser(@RequestBody OutboundUser user,HttpServletRequest request) {
        Integer roleId=user.getRoleId();
        if(roleId==null||roleId==0){

        }else{
            user.setRoleId(null);
        }
        if(loginService.updateUserById(user)){
            loginService.addUserRole(user.getId(),roleId);
            return ResultGenerator.setCustomResult(200,"修改成功");
        }else{
            return ResultGenerator.setCustomResult(4000,"修改失败");
        }

    }
    /**
     * 删除用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public Result delUser(@RequestBody PubParam pubParam,HttpServletRequest request) {
        if(loginService.delUser(pubParam.getIds())){
            loginService.delUserRole(pubParam.getIds());
            return ResultGenerator.setCustomResult(200,"删除成功");
        }else{
            return ResultGenerator.setCustomResult(4000,"删除失败");
        }

    }
    /**
     * 用户密码验证
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkPassword")
    public Result checkPasswork(@RequestBody OutboundUser user,HttpServletRequest request) {

        List<OutboundUser> userList = loginService.checkPassword(user);
        Map<String,String > map=new HashMap<>();
        if(null!=userList && !userList.isEmpty()){
            map.put("msg","Success");
            return ResultGenerator.setCustomResult(200,"密码输入正确",map);
        }else{
            map.put("msg","Error");
            return ResultGenerator.setCustomResult(4000,"密码输入错误",map);
        }

    }

    /**
     * 用户名验证
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUserName")
    public Result checkUserName(@RequestBody OutboundUser user,HttpServletRequest request)throws IOException {
        Map<String,String > map=new HashMap<>();
        OutboundUser outboundUser=loginService.getUser(user.getId());
        if(outboundUser!=null && user.getUserName().equals(outboundUser.getUserName())){
            map.put("msg","Success");
            return ResultGenerator.setCustomResult(200,"用户名未修改",map);
        }
        List<OutboundUser> userList = loginService.checkUserName(user.getUserName());
        if(null!=userList && !userList.isEmpty()){
            map.put("msg","Error");
            return ResultGenerator.setCustomResult(4000,"该用户名已存在本市数据库，建议使用警员编号作为用户名",map);
        }else{
            map.put("msg","Success");
            return ResultGenerator.setCustomResult(200,"该用户名可以使用",map);
        }

    }

    /**
     * 警员编号验证
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkCard")
    public Result checkCard(@RequestBody OutboundUser user,HttpServletRequest request)throws IOException {
        Map<String,String > map=new HashMap<>();
        OutboundUser outboundUser=loginService.getUser(user.getId());
        if(outboundUser!=null && user.getCard().equals(outboundUser.getCard())){
            map.put("msg","Success");
            return ResultGenerator.setCustomResult(200,"用户名未修改",map);
        }
        Integer id = loginService.checkCard(user.getCard());

        if(id!=null){
            map.put("msg","Error");
            return ResultGenerator.setCustomResult(4000,"该警员编号已存在本市数据库，请核实编号",map);
        }else{
            map.put("msg","Success");
            return ResultGenerator.setCustomResult(200,"该警员编号未被使用",map);
        }

    }
    /**
     * 获取菜单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenu")
    public Result getMenu(@RequestBody PubParam pubParam,HttpServletRequest request) throws IOException {
        return ResultGenerator.genSuccessResult(loginService.getMenu(pubParam.getRoleId()));
    }

    @ResponseBody
    @RequestMapping("/getSession")
    public Result getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        OutboundSession outboundSession = (OutboundSession) session.getAttribute("user");
        if (outboundSession!=null) {

            return ResultGenerator.genSuccessResult(outboundSession);
        }else{
            return ResultGenerator.setCustomResult(4000,"请先登录",null);
        }

    }

    /**
     * 登出 清除Session
     * @param request
     * @return
     */
    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }


}
