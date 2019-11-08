package com.along.outboundmanage.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.along.outboundmanage.model.*;
import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.service.AreaService;
import com.along.outboundmanage.service.RouteService;
import com.along.outboundmanage.service.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.getNowData;
/**
 * 任务管理
 * @author why
 * @return
 * @description 任务管理
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {
	@Resource
	TaskService taskService;
	@Resource
	AreaService areaService;
	@Resource
	RouteService routeService;
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
	public Result upTask(@RequestBody Map<String, Object> map ){
		List<Integer> clist = (List<Integer>) map.get("carIds");
		List<Integer> wlist = (List<Integer>) map.get("watchIds");
		wlist =wlist.stream().distinct().collect(Collectors.toList());
		List<Integer> hlist = (List<Integer>) map.get("handsetIds");
		hlist =hlist.stream().distinct().collect(Collectors.toList());
		List<Integer> glist = (List<Integer>) map.get("grapplersIds");
		glist =glist.stream().distinct().collect(Collectors.toList());
		OutboundTaskV2 outboundTask=new OutboundTaskV2.Builder().id(Integer.valueOf(map.get("id")+""))
				.name(map.get("name")+"").describe(map.get("describe")+"").routeId(Integer.valueOf(map.get("routeId")+""))
				.routeName(map.get("routeName")+"").type(Integer.valueOf(map.get("type")+"")).areaId(Integer.valueOf(map.get("areaId")+""))
				.watchIds(wlist.toArray(new Integer[clist.size()])).grapplersIds(glist.toArray(new Integer[clist.size()]))
				.handsetIds(hlist.toArray(new Integer[clist.size()])).carIds(clist.toArray(new Integer[clist.size()]))
				.build();
		List<OutboundPoliceForSel> policeList2=   (List<OutboundPoliceForSel>) map.get("policeIds");
		Integer[] pids=new Integer[policeList2.size()];
		for (int i = 0; i < policeList2.size(); i++) {
			net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(policeList2.get(i)); // 将数据转成json字符串
			OutboundPoliceForSel per = (OutboundPoliceForSel)JSONObject.toBean(jsonObject, OutboundPoliceForSel.class); //将json转成需要的对象
			pids[i]=per.getId();
		}
		outboundTask.setPoliceIds(pids);
		List<OutboundPrisoner> prisonerList2=   (List<OutboundPrisoner>) map.get("prisonerIds");
		Integer[] prids=new Integer[prisonerList2.size()];
		for (int i = 0; i < prisonerList2.size(); i++) {
			net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(prisonerList2.get(i)); // 将数据转成json字符串
			OutboundPrisoner per = (OutboundPrisoner)JSONObject.toBean(jsonObject, OutboundPrisoner.class); //将json转成需要的对象
			prids[i]=per.getId();
		}
		outboundTask.setPrisonerIds(prids);
		outboundTask.setStartTime(map.get("startTime")+"");
		String[]arr=routeService.getOd(outboundTask.getRouteId()).split(",");
		outboundTask.setOrigin(arr[0]);
		outboundTask.setDestination(arr[1]);
		outboundTask.setRouteName(arr[2]);
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

	/*@ResponseBody
	@RequestMapping("/addTask")
	public Result addTask(@RequestBody  OutboundTaskDesc outboundTask, HttpServletRequest request){
		Map<String, Object> returnTask=taskService.insertTask(outboundTask);
		if(returnTask==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnTask);
		}
	}
*/
	@RequestMapping("/addTask")
	public Result addTask(@RequestBody Map<String, Object> map, HttpServletRequest request){
		List<Integer> clist = (List<Integer>) map.get("carIds");
		List<Integer> wlist = (List<Integer>) map.get("watchIds");
		wlist =wlist.stream().distinct().collect(Collectors.toList());
		List<Integer> hlist = (List<Integer>) map.get("handsetIds");
		hlist =hlist.stream().distinct().collect(Collectors.toList());
		List<Integer> glist = (List<Integer>) map.get("grapplersIds");
		glist =glist.stream().distinct().collect(Collectors.toList());
		OutboundTaskV2 outboundTask=new OutboundTaskV2.Builder()
				.areaId(Integer.valueOf(map.get("areaId")+""))
				.name(map.get("name")+"").describe(map.get("describe")+"").routeId(Integer.valueOf(map.get("routeId")+""))
				.routeName(map.get("routeName")+"").type(Integer.valueOf(map.get("type")+""))
				.watchIds(wlist.toArray(new Integer[clist.size()])).grapplersIds(glist.toArray(new Integer[clist.size()]))
				.handsetIds(hlist.toArray(new Integer[clist.size()])).carIds(clist.toArray(new Integer[clist.size()]))
				.build();
		List<OutboundPoliceForSel> policeList2=   (List<OutboundPoliceForSel>) map.get("policeIds");
		Integer[] pids=new Integer[policeList2.size()];
		for (int i = 0; i < policeList2.size(); i++) {
			net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(policeList2.get(i)); // 将数据转成json字符串
			OutboundPoliceForSel per = (OutboundPoliceForSel)JSONObject.toBean(jsonObject, OutboundPoliceForSel.class); //将json转成需要的对象
			pids[i]=per.getId();
		}
		outboundTask.setPoliceIds(pids);
		List<OutboundPrisoner> prisonerList2=   (List<OutboundPrisoner>) map.get("prisonerIds");
		Integer[] prids=new Integer[prisonerList2.size()];
		for (int i = 0; i < prisonerList2.size(); i++) {
			net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(prisonerList2.get(i)); // 将数据转成json字符串
			OutboundPrisoner per = (OutboundPrisoner)JSONObject.toBean(jsonObject, OutboundPrisoner.class); //将json转成需要的对象
			prids[i]=per.getId();
		}
		outboundTask.setPrisonerIds(prids);
		outboundTask.setStartTime(map.get("startTime")+"");
		String[]arr=routeService.getOd(outboundTask.getRouteId()).split(",");
		outboundTask.setOrigin(arr[0]);
		outboundTask.setDestination(arr[1]);
		outboundTask.setRouteName(arr[2]);
		outboundTask.setStatus(0);
		OutboundTaskV2 returnTask=taskService.addTask(outboundTask);
		if(returnTask==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnTask);
		}
	}

	//重新分配干警和设备
	@ResponseBody
	@RequestMapping("/addPoliceEquip")
	public Result addPoliceEquip(@RequestBody Map<String,Object> map, HttpServletRequest request){
		Map<String, Object> returnTask=taskService.addpoliceEquip(map);
		if(returnTask==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnTask);
		}
	}

	//重新分配犯人和设备
	@ResponseBody
	@RequestMapping("/addPrisonerEquip")
	public Result addPrisonerEquip(@RequestBody Map<String,Object> map, HttpServletRequest request){
		Map<String, Object> returnTask=taskService.addPrisonerEquip(map);
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
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String status="3";
		String time= " start_time='"+(dateFormat.format(calendar.getTime()))+"' ";
		Map<String, String> map=new HashMap<>();
		if(taskService.startTask(pubParam.getIds(),status,time)){
			map.put("data","Success");
			return ResultGenerator.setCustomResult(200,"修改成功",map);
		}else{
			map.put("data","Error");
			return ResultGenerator.setCustomResult(4000,"修改失败",map);
		}
	}
	//结束任务(备份历史数据,关系表清空  )
	@ResponseBody
	@RequestMapping("/endTask")
	public Result endTask(@RequestBody PubParam pubParam){
		//  0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
		String status="4";
		String time= " end_time='"+(getNowData("yyyy-MM-dd HH:mm:ss"))+"' ";
		Map<String, String> map=new HashMap<>();
		if(taskService.endTask(pubParam.getIds(),status,time)){
			map.put("data","Success");
			return ResultGenerator.setCustomResult(200,"修改成功",map);
		}else{
			map.put("data","Error");
			return ResultGenerator.setCustomResult(4000,"修改失败",map);
		}
	}

	/*****************************我的任务（警员）**********************************************/
	//当前任务
	@ResponseBody
	@RequestMapping("/getMyCurTaskList")
	public Result getMyCurTaskList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());

		List<OutboundTaskJson> allList = taskService.getMyCurrTaskByStatus("1,3",pubParam.getAreaId()+"",pubParam.getUserId());
		PageInfo<OutboundTaskJson> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	//历史任务
	@ResponseBody
	@RequestMapping("/getMyHisTaskList")
	public Result getMyHisTaskList(@RequestBody PubParam pubParam){
		System.out.println("123456");
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
	@RequestMapping("/getCountTask")
	public Result getCountTask(@RequestBody PubParam pubParam){

		String areaIds=String.join(",",areaService.getAreaIdsByPar(pubParam.getAreaId()).stream().map(e->e+"").collect(Collectors.toList()));
		Map<String,String> data=new HashMap<>();
		if (areaIds!=null && !areaIds.equals("")) {
			Integer count = taskService.getCountTask("0", areaIds);
			data.put("count",count+"");
		}else{
			data.put("count","0");
		}

		return ResultGenerator.genSuccessResult(data);
	}
	//任务清单
	@ResponseBody
	@RequestMapping("/getTaskAuditList")
	public Result getTaskAuditList(@RequestBody PubParam pubParam){

		String areaIds=String.join(",",areaService.getAreaIdsByPar(pubParam.getAreaId()).stream().map(e->e+"").collect(Collectors.toList()));
		List<OutboundTask> allList =new ArrayList<>();
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		if (areaIds!=null && !areaIds.equals("")) {
			allList = taskService.getTaskByStatus("0,1,2,4", areaIds);
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
		if(taskService.checkTaskId(outboundTask)){
			return ResultGenerator.setCustomResult(200,"修改成功","修改成功");
		}else{
			return ResultGenerator.setCustomResult(4000,"修改失败","修改失败");
		}
	}
	/*****************************任务中心**********************************************/
	//任务清单
	@ResponseBody
	@RequestMapping("/getTaskCenterList")
	public Result getTaskCenterList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundTaskJson> allList = taskService.getTaskByStatus2("3",pubParam.getAreaId()+"");
		PageInfo<OutboundTaskJson> pageInfo = new PageInfo<>(allList);
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
