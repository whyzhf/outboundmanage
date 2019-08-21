package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.service.ViewsService;
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
	public Result getMap(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getMap(areaId));
	}
	//在押
	@ResponseBody
	@RequestMapping("/getCustody")
	public Result getCustody(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getCustody(areaId));
	}

	//外出
	@ResponseBody
	@RequestMapping("/getOut")
	public Result getOut(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getOut(areaId));
	}
	//指认
	@ResponseBody
	@RequestMapping("/getDesignate")
	public Result getDesignate(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getDesignate(areaId));
	}
	//出庭，投牢，审讯
	@ResponseBody
	@RequestMapping("/getOthers")
	public Result getOthers(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getOthers(areaId));
	}

	//全局统计
	@ResponseBody
	@RequestMapping("/getPie")
	public Result getPie(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getPie(areaId));
	}

	/*****************************地市视图**********************************************/
	//地域图
	@ResponseBody
	@RequestMapping("/getSecMap")
	public Result getSecMap(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecMap(areaId));
	}
	//在押
	@ResponseBody
	@RequestMapping("/getSecCustody")
	public Result getSecCustody(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecCustody(areaId));
	}

	//外出
	@ResponseBody
	@RequestMapping("/getSecOut")
	public Result getSecOut(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecOut(areaId));
	}

	//指认
	@ResponseBody
	@RequestMapping("/getSecDesignate")
	public Result getSecDesignate(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecDesignate(areaId));
	}
	//出庭，投牢，审讯
	@ResponseBody
	@RequestMapping("/getSecOthers")
	public Result getSecOthers(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecOthers(areaId));
	}

	//全局统计
	@ResponseBody
	@RequestMapping("/getSecPie")
	public Result getSecPie(String areaId){
		return ResultGenerator.genSuccessResult(viewsService.getSecPie(areaId));
	}

}
