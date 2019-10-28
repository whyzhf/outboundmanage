package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.RoadLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * 路线管理
 * @author why
 * @return
 * @description 已利用高德地图处理
 */
@RestController
@RequestMapping(value = "/roadlog")
public class RoadLogController {
	@Resource
	RoadLogService roadLogService;

	/***************************************已处理****************************************************/
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
