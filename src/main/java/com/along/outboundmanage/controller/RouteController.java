package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.RouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/route")
public class RouteController {
	@Resource
	RouteService routeService;
	/*****************************路线管理**********************************************/
	@ResponseBody
	@RequestMapping("/getRouteList")
	public Result getRouteList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundRoute> allList = routeService.getAllRoute(pubParam.getAreaId());
		PageInfo<OutboundRoute> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@ResponseBody
	@RequestMapping("/upRoute")
	public Result upRoute(@RequestBody  OutboundRoute outboundRoute){
		if(routeService.updateRouteById(outboundRoute)){
			return ResultGenerator.setCustomResult(200,"修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}
	}

	@ResponseBody
	@RequestMapping("/delRoute")
	public Result delRoute(@RequestBody PubParam pubParam){
		if(routeService.delRoute(pubParam.getIds())){
			return ResultGenerator.setCustomResult(200,"删除成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}
	}

	@ResponseBody
	@RequestMapping("/addRoute")
	public Result addRoute(@RequestBody  OutboundRoute outboundRoute, HttpServletRequest request){
		OutboundRoute returnRoute=routeService.insertRoute(outboundRoute);
		if(returnRoute==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnRoute);
		}
	}
	/***************************************未完成****************************************************/
	//存储路线（gps位置）
	@ResponseBody
	@RequestMapping("/saveGpsRoute")
	public Result saveGpsRoute(@RequestBody OutboundRoadlog outboundRoadlog, HttpServletRequest request){
			return ResultGenerator.genSuccessResult();
	}
	//存储围栏（gps位置）
	@ResponseBody
	@RequestMapping("/saveFence")
	public Result saveFence(@RequestBody  OutboundRoadlog outboundRoadlog, HttpServletRequest request){
		return ResultGenerator.genSuccessResult();
	}
	//清除记录（gps位置）
	@ResponseBody
	@RequestMapping("/delRoadLog")
	public Result delRoadLog(@RequestBody PubParam pubParam, HttpServletRequest request){

		return ResultGenerator.genSuccessResult();
	}

	//当前位置（gps位置）
	@ResponseBody
	@RequestMapping("/currRoad")
	public Result currRoad(@RequestBody PubParam pubParam, HttpServletRequest request){

		return ResultGenerator.genSuccessResult();
	}
}
