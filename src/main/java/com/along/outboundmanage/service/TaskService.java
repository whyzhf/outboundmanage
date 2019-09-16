package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundTask;
import com.along.outboundmanage.model.OutboundTaskDesc;
import com.along.outboundmanage.model.OutboundTaskV2;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

public interface TaskService {

	//查询
	List<OutboundTask> getAllTask(int areaId);
	//查询
	List<OutboundTask> getTaskByStatus(String status,String areaId);
	Integer getCountTask(String status,String areaId);
	//修改
	//boolean updateTaskById( OutboundTaskDesc outboundTask);
	boolean updateTaskById( OutboundTaskV2 outboundTask);
	//结束任务
	boolean endTask(String ids,String status,String time);
	//开始任务
	boolean startTask(String ids,String status,String time);
	//修改状态
	boolean updateTaskStatusById( OutboundTask outboundTask);


	//删除
	boolean delTask( String ids);
	//新增
	Map<String, Object> insertTask(OutboundTaskDesc outboundTask);

	@Transactional(rollbackFor = Exception.class)
	OutboundTaskV2 addTask(OutboundTaskV2 outboundTask);

	@Transactional(rollbackFor = Exception.class)
	Map<String, Object> addpoliceEquip(Map<String, Object> map);

	@Transactional(rollbackFor = Exception.class)
	Map<String, Object> addPrisonerEquip(Map<String, Object> map);

	//我的任务（未完成的任务）
	List<OutboundTask>  getMyCurrTaskByStatus(String status,String areaId,int userId);
	//我的任务（历史的任务）
	List<String> getAllMyTaskHistoryFir(int userId,String areaId);
	//我的任务（历史的任务详情）
	String getMyTaskHistorySec(int userId,int taskId,int areaId);
	//任务详情
	Map<String,Object> getTaskDesc(int id);
	//任务结束，导入his表
	//boolean exportHistory(int taskId,int userId,String firJson,String secJson,int areaId);


}
