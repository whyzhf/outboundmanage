package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import com.along.outboundmanage.model.OutboundRouteJson;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.RouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.along.outboundmanage.utill.DataUtil.getNowData;

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
		List<OutboundRouteJson> allList = routeService.getAllRoute(pubParam.getAreaId());
		PageInfo<OutboundRouteJson> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	@ResponseBody
	@RequestMapping("/getRoute")
	public Result getRoute(@RequestBody PubParam pubParam){

		List<OutboundRouteJson> allList = routeService.getAllRoute(pubParam.getAreaId());

		return ResultGenerator.genSuccessResult(allList);
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
		outboundRoute.setUptime(getNowData("yyyy-MM-dd HH:mm:ss"));
		OutboundRoute returnRoute=routeService.insertRoute(outboundRoute);
		OutboundRouteJson outboundRouteJson=new OutboundRouteJson();
		outboundRouteJson.setId(returnRoute.getId());
		outboundRouteJson.setAreaId(returnRoute.getAreaId());
		outboundRouteJson.setDestination(returnRoute.getDestination());
		outboundRouteJson.setDistance(returnRoute.getDistance());
		outboundRouteJson.setName(returnRoute.getName());
		outboundRouteJson.setOrigin(returnRoute.getOrigin());
		outboundRouteJson.setUptime(returnRoute.getUptime());
		outboundRouteJson.setRail(returnRoute.getRail());
		if(returnRoute==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{

			return ResultGenerator.genSuccessResult(outboundRouteJson);
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
		//{["12.36,35.69","12.36,35.69"]}
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
