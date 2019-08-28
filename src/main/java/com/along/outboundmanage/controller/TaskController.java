package com.along.outboundmanage.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundTask;
import com.along.outboundmanage.model.OutboundTaskDesc;
import com.along.outboundmanage.model.PubParam;
import com.along.outboundmanage.service.AreaService;
import com.along.outboundmanage.service.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.getNowData;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
	@Resource
	TaskService taskService;
	@Resource
	AreaService areaService;
	/*****************************任务管理**********************************************/
	@ResponseBody
	@RequestMapping("/getTaskList")
	public Result getTaskList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundTask> allPolice = taskService.getAllTask(pubParam.getAreaId());
		PageInfo<OutboundTask> pageInfo = new PageInfo<>(allPolice);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	//任务详情
	@ResponseBody
	@RequestMapping("/getTaskDesc")
	public Result getTaskDesc(@RequestBody PubParam pubParam){
		return ResultGenerator.genSuccessResult( taskService.getTaskDesc(pubParam.getTaskId()));
	}
	@ResponseBody
	@RequestMapping("/upTask")
	public Result upTask(@RequestBody OutboundTaskDesc outboundTask){
		if(taskService.updateTaskById(outboundTask)){
			return ResultGenerator.setCustomResult(200,"修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}
	}

	@ResponseBody
	@RequestMapping("/delTask")
	public Result delTask(@RequestBody PubParam pubParam){
		if(taskService.delTask(pubParam.getIds())){
			return ResultGenerator.setCustomResult(200,"删除成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}
	}

	@ResponseBody
	@RequestMapping("/addTask")
	public Result addTask(@RequestBody  OutboundTaskDesc outboundTask, HttpServletRequest request){
		Map<String, Object> returnTask=taskService.insertTask(outboundTask);
		if(returnTask==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnTask);
		}
	}
	//开始任务
	@ResponseBody
	@RequestMapping("/startTask")
	public Result startTask(@RequestBody PubParam pubParam){
		//  0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
		Calendar calendar= Calendar.getInstance();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String status="3";
		String time= " start_time='"+(dateFormat.format(calendar.getTime()))+"' ";
		if(taskService.startTask(pubParam.getIds(),status,time)){
			return ResultGenerator.setCustomResult(200,"修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}
	}
	//结束任务(备份历史数据,关系表清空  未测)
	@ResponseBody
	@RequestMapping("/endTask")
	public Result endTask(@RequestBody PubParam pubParam){
		//  0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
		String status="4";
		String time= " start_time='"+(getNowData("yyyy-MM-dd hh:mm:ss"))+"' ";
		if(taskService.endTask(pubParam.getIds(),status,time)){
			return ResultGenerator.setCustomResult(200,"修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}
	}

	/*****************************我的任务（警员）**********************************************/
	//当前任务
	@ResponseBody
	@RequestMapping("/getMyCurTaskList")
	public Result getMyCurTaskList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundTask> allList = taskService.getMyCurrTaskByStatus("1,3",pubParam.getAreaId()+"",pubParam.getUserId());
		PageInfo<OutboundTask> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	//历史任务
	@ResponseBody
	@RequestMapping("/getMyHisTaskList")
	public Result getMyHisTaskList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<String> allList = taskService.getAllMyTaskHistoryFir(pubParam.getUserId(),pubParam.getAreaId()+"");
		PageInfo<Object> pageInfo = new PageInfo<>(allList.stream().map(e-> JSON.parseObject(e)).collect(Collectors.toList()));
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	//历史任务(详情)
	@ResponseBody
	@RequestMapping("/getMyHisTaskSec")
	public Result getMyHisTaskSec(@RequestBody PubParam pubParam){

		String allList = taskService.getMyTaskHistorySec(pubParam.getUserId(),pubParam.getTaskId(),pubParam.getAreaId());

		return ResultGenerator.genSuccessResult(JSON.parseObject(allList));
	}
	/*****************************任务审批（地市管理员）**********************************************/
	//任务清单
	@ResponseBody
	@RequestMapping("/getTaskAuditList")
	public Result getTaskAuditList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		String areaIds=String.join(",",areaService.getAreaIdsByPar(pubParam.getAreaId()).stream().map(e->e+"").collect(Collectors.toList()));
		List<OutboundTask> allList =new ArrayList<>();
		if (areaIds!=null && !areaIds.equals("")) {
			allList = taskService.getTaskByStatus("0,1,2", areaIds);
		}else{
			allList =null;
		}
		PageInfo<OutboundTask> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	//任务审核
	@ResponseBody
	@RequestMapping("/checkTask")
	public Result checkTask(@RequestBody  OutboundTask outboundTask){
		//  0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
		if(taskService.updateTaskStatusById(outboundTask)){
			return ResultGenerator.setCustomResult(200,"修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}
	}
	/*****************************任务中心**********************************************/
	//任务清单
	@ResponseBody
	@RequestMapping("/getTaskCenterList")
	public Result getTaskCenterList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());

		List<OutboundTask> allList = taskService.getTaskByStatus("3",pubParam.getAreaId()+"");
		PageInfo<OutboundTask> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/***************************************未完成****************************************************/
	//当前位置
	@ResponseBody
	@RequestMapping("/getCurrRoute")
	public Result getCurrRoute( ){

		return ResultGenerator.genSuccessResult();
	}

	//历史轨迹
	@ResponseBody
	@RequestMapping("/getHisRoute")
	public Result getHisRoute( ){

		return ResultGenerator.genSuccessResult();
	}

	//抓捕路线
	@ResponseBody
	@RequestMapping("/getArrestRoute")
	public Result getArrestRoute( ){

		return ResultGenerator.genSuccessResult();
	}

	//制伏
	@ResponseBody
	@RequestMapping("/getSubdue")
	public Result getSubdue( ){

		return ResultGenerator.genSuccessResult();
	}

}
