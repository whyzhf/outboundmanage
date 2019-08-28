package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.ViewsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/views")
public class ViewsController {
	@Resource
	ViewsService viewsService;

	/*****************************省厅视图**********************************************/
	//地域图
	@ResponseBody
	@RequestMapping("/getMap")
	public Result getMap(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getMap(pubParam.getAreaId()+""));
	}
	//在押
	@ResponseBody
	@RequestMapping("/getCustody")
	public Result getCustody(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getCustody(pubParam.getAreaId()+""));
	}

	//外出
	@ResponseBody
	@RequestMapping("/getOut")
	public Result getOut(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getOut(pubParam.getAreaId()+""));
	}
	//指认
	@ResponseBody
	@RequestMapping("/getDesignate")
	public Result getDesignate(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getDesignate(pubParam.getAreaId()+""));
	}
	//出庭，投牢，审讯
	@ResponseBody
	@RequestMapping("/getOthers")
	public Result getOthers(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getOthers(pubParam.getAreaId()+""));
	}

	//全局统计
	@ResponseBody
	@RequestMapping("/getPie")
	public Result getPie(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getPie(pubParam.getAreaId()+""));
	}

	/*****************************地市视图**********************************************/
	//地域图
	@ResponseBody
	@RequestMapping("/getSecMap")
	public Result getSecMap(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecMap(pubParam.getAreaId()+""));
	}
	//在押
	@ResponseBody
	@RequestMapping("/getSecCustody")
	public Result getSecCustody(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecCustody(pubParam.getAreaId()+""));
	}

	//外出
	@ResponseBody
	@RequestMapping("/getSecOut")
	public Result getSecOut(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecOut(pubParam.getAreaId()+""));
	}

	//指认
	@ResponseBody
	@RequestMapping("/getSecDesignate")
	public Result getSecDesignate(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecDesignate(pubParam.getAreaId()+""));
	}
	//出庭，投牢，审讯
	@ResponseBody
	@RequestMapping("/getSecOthers")
	public Result getSecOthers(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecOthers(pubParam.getAreaId()+""));
	}

	//全局统计
	@ResponseBody
	@RequestMapping("/getSecPie")
	public Result getSecPie(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult(viewsService.getSecPie(pubParam.getAreaId()+""));
	}

}
