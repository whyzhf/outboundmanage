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
import static com.along.outboundmanage.utill.GeneralUtils.getJsonArr;

/**
 * 路线管理
 * @author why
 * @return
 * @description 路线管理
 */
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
	@RequestMapping("/getRouteById")
	public Result getRouteById(@RequestBody PubParam pubParam){
		OutboundRouteJson json = routeService.getRouteById(pubParam.getAreaId());

		return ResultGenerator.genSuccessResult(json);
	}

	@ResponseBody
	@RequestMapping("/upRoute")
	public Result upRoute(@RequestBody  OutboundRouteJson outboundRoute){
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
	public Result addRoute(@RequestBody  OutboundRouteJson outboundRoute, HttpServletRequest request){
		outboundRoute.setUptime(getNowData("yyyy-MM-dd HH:mm:ss"));
		OutboundRoute returnRoute=routeService.insertRoute(outboundRoute);
		String[] strArr = new String[2];
		double[] OriginLngLat = new double[2];
		double[] DestinationLngLat = new double[2];
		OutboundRouteJson outboundRouteJson=new OutboundRouteJson();
		outboundRouteJson.setId(returnRoute.getId());
		outboundRouteJson.setAreaId(returnRoute.getAreaId());
		outboundRouteJson.setDestination(returnRoute.getDestination());
		outboundRouteJson.setDistance(returnRoute.getDistance());
		outboundRouteJson.setName(returnRoute.getName());
		outboundRouteJson.setOrigin(returnRoute.getOrigin());
		outboundRouteJson.setUptime(returnRoute.getUptime());
		outboundRouteJson.setRail(getJsonArr(returnRoute.getRail()));
		strArr = returnRoute.getOriginLngLat().split(",");
		if(strArr.length>0) {
			OriginLngLat[0] = Double.parseDouble(strArr[0]);
			OriginLngLat[1] = Double.parseDouble(strArr[1]);
		}
		outboundRouteJson.setOriginLngLat(OriginLngLat);
		strArr = returnRoute.getDestinationLngLat().split(",");
		if(strArr.length>0) {
			DestinationLngLat[0] = Double.parseDouble(strArr[0]);
			DestinationLngLat[1] = Double.parseDouble(strArr[1]);
		}
		outboundRouteJson.setDestinationLngLat(DestinationLngLat);

		if(returnRoute==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{

			return ResultGenerator.genSuccessResult(outboundRouteJson);
		}
	}

}
