package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface TaskDao {
	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as routeName,r.originLngLat,r.destinationLngLat" +
			" FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" WHERE t.area_id=#{areaId} order by  t.status <> 3 ,t.id Desc" )
	List<OutboundTask> getAllTaskByAreaId(@Param("areaId") int areaId);
	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as routeName,r.originLngLat,r.destinationLngLat" +
			" FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" WHERE t.area_id=#{areaId} and t.status in(${status}) order by  t.status <> 3,t.id Desc" )
	List<OutboundTask> getTaskByAreaIdStatus(@Param("areaId") int areaId,@Param("status") String status);
	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as routeName,r.originLngLat,r.destinationLngLat" +
			" FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" WHERE t.id in(${ids}) order by t.id" )
	List<OutboundTask> getAllTaskByIds(@Param("ids") String ids);


	@Update("UPDATE outbound_task\n" +
			" SET status=${status}, " +
			" ${time}"+
			" WHERE id in(${ids})" )
	boolean upTaskStatusById(@Param("ids") String ids,@Param("status") String status,@Param("time") String time);

	/*@UpdateProvider(type = SqlProvider.class, method = "updataTask")
	boolean updateTaskById(@Param("OutboundTask") OutboundTask outboundTask);*/
	@Update("UPDATE outbound_task\n" +
			"SET name=#{OutboundTask.name}, origin=#{OutboundTask.origin}, destination=#{OutboundTask.destination}," +
			" start_time=#{OutboundTask.startTime}, end_time=#{OutboundTask.endTime}," +
			" `describe`=#{OutboundTask.describe}, route_id=#{OutboundTask.routeId}, `type`=#{OutboundTask.type}, remarks=#{OutboundTask.remarks}, area_id=#{OutboundTask.areaId}" +
			" WHERE id=#{OutboundTask.id}")
	boolean updateTaskById(@Param("OutboundTask") OutboundTask outboundTask);

	@Update("UPDATE outbound_task\n" +
			"SET  remarks=#{OutboundTask.remarks},status=#{OutboundTask.status}" +
			" WHERE id=#{OutboundTask.id}")
	boolean checkTaskId(@Param("OutboundTask") OutboundTask outboundTask);

	@Insert("INSERT INTO outbound_task" +
			" ( name, origin, destination, start_time, end_time, status, `describe`, route_id, `type`, remarks, area_id)" +
			" VALUES(#{OutboundTask.name},#{OutboundTask.origin},#{OutboundTask.destination}, " +
			"#{OutboundTask.startTime},#{OutboundTask.endTime},0," +
			" #{OutboundTask.describe},#{OutboundTask.routeId}, #{OutboundTask.type}, " +
			"#{OutboundTask.remarks},#{OutboundTask.areaId})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundTask.id", keyColumn = "id")
	int insertTask(@Param("OutboundTask") OutboundTask outboundTask);


	@Delete("delete from outbound_task where id in (${ids})")
	boolean delTask(@Param("ids") String ids);

	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as route_name,a.name as areaName,r.rail as rail\n" +
			" ,r.originLngLat,r.destinationLngLat FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" left join outbound_area a on t.area_id=a.id\n" +
			" WHERE t.area_id in (${areaId}) and  t.status in (${status})" +
			"  order by t.status, t.id, t.area_id" )
	List<OutboundTaskJson> getAllTaskByStatus2(@Param("status")String status,@Param("areaId") String areaId);

	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as route_name,a.name as areaName,r.rail as rail\n" +
			" ,r.originLngLat,r.destinationLngLat FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" left join outbound_area a on t.area_id=a.id\n" +
			" WHERE t.id in (${taskIds}) and  t.status in (3,4) " +
			"  order by t.status, t.id, t.area_id" )
	OutboundTaskJson getTaskByIds(@Param("taskIds")String taskIds);

	@Select(" SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as route_name,a.name as areaName\n" +
			",r.originLngLat,r.destinationLngLat FROM outbound_task t\n" +
			" left join outbound_route r on t.route_id=r.id\n" +
			" left join outbound_area a on t.area_id=a.id\n" +
			" WHERE t.area_id in (${areaId}) and  t.status in (${status})" +
			"  order by t.status, t.id, t.area_id" )
	List<OutboundTask> getAllTaskByStatus(@Param("status")String status,@Param("areaId") String areaId);



	@Select(" SELECT count(id)" +
			" FROM outbound_task " +
			" WHERE area_id in (${areaId}) and  status in (${status})"
			)
	Integer getCountTaskByStatus(@Param("status")String status,@Param("areaId") String areaId);
	//任务详情
	@Select("SELECT t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.`describe`, t.route_id,r.name as route_name, t.`type`, t.area_id,\n" +
			" tp.police_id as police  ,tc.car_id  as car ,tpr.prisoner_id as prisoner \n" +
			" FROM outbound_task t\n" +
			" left join outbound_route r on r.id =t.route_id" +
			" left join (select task_id,group_concat(police_id Separator ',') as police_id from  outbound_task_police_rel where task_id=#{id}) tp on t.id =tp.task_id \n" +
			" left join  (select task_id,group_concat(car_id Separator ',') as car_id from  outbound_task_car_rel where task_id=#{id}) tc on t.id =tc.task_id\n" +
			" left join  (select task_id,group_concat(prisoner_id Separator ',') as prisoner_id from outbound_task_prisoner_rel where task_id=#{id}) tpr on t.id =tpr.task_id\n" +
			" where t.id=#{id}")
	OutboundTaskV2 getTaskDesc( @Param("id") int id);

	//我的当前任务（一级）
	@Select(" SELECT distinct  t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id,r.name as route_name,r.rail as rail" +
			" ,r.originLngLat,r.destinationLngLat from \n" +
			" (SELECT distinct t.id, t.name, t.origin, t.destination, t.start_time, t.end_time, t.status, t.describe, t.route_id, t.type, t.remarks, t.area_id" +
			"    FROM outbound_task t\n" +
			"    left join outbound_task_police_rel pr on pr.task_id=t.id\n" +
			"    left join outbound_police p on p.id=pr.police_id\n" +
			"    where p.user_id=#{userId}" +
			" )t\n" +
			" left join outbound_route r on t.route_id=r.id \n" +
			" WHERE t.area_id in (${areaId}) and  t.status in (${status}) \n" +
			" order by  t.status,t.area_id" )
	List<OutboundTaskJson> getMyCurrTaskByStatus(@Param("status")String status,@Param("areaId") String areaId,@Param("userId") int userId);
	//我的任务（历史记录）（一级）
	@Select("SELECT  firjson " +
			" FROM outbound_task_history where user_id=#{userId} and area_id=#{areaId}")
	List<String> getAllMyTaskHistoryFir(@Param("userId")int userId,@Param("areaId") String areaId);

	@InsertProvider(type = SqlProvider.class, method = "addTaskHis")
	boolean addMyTaskHistory(@Param("list") List<TaskHistory> list);

	//我的任务（历史记录）（详情）
	@Select("SELECT  secjson " +
			" FROM outbound_task_history where user_id=#{userId} and task_id=#{taskId} and area_id=#{areaId}")
	String getMyTaskHistorySec(@Param("userId")int userId,@Param("taskId")int taskId,@Param("areaId") int areaId);


	//添加关系表 Task - Polic
	@InsertProvider(type = SqlProvider.class, method = "addTaskPolic")
	boolean addTaskPolic(@Param("tpIds")List<KandV> tpIds);

	@Delete("Delete from  outbound_task_police_rel where task_id in (${taskId})")
	boolean delTaskPolic(@Param("taskId")String taskId);

	//添加关系表 Task - Prisoner
	@InsertProvider(type = SqlProvider.class, method = "addTaskPrisoner")
	boolean addTaskPrisoner(@Param("tpIds")List<KandV>tpIds);

	@Delete("Delete from  outbound_task_prisoner_rel where task_id  in (${taskId})")
	boolean delTaskPrisoner(@Param("taskId")String taskId);

	//添加关系表 Task - Car
	@InsertProvider(type = SqlProvider.class, method = "addTaskCar")
	boolean addTaskCar(@Param("tpIds")List<KandV>tpIds);

	@Delete("Delete from  outbound_task_car_rel where task_id  in (${taskId})")
	boolean delTaskCar(@Param("taskId")String taskId);

	@Update("UPDATE outbound_police\n" +
			" SET equipment_id=null, equipment_id2=null \n" +
			" WHERE id in (${ids})")
	boolean clearPoliceEquip(@Param("ids") String ids);
	@Update("UPDATE outbound_prisoner\n" +
			" SET equipment_id=null \n" +
			" WHERE id in (${ids})")
	boolean clearPrisonerEquip(@Param("ids") String ids);
	@Update("UPDATE outbound_car\n" +
			" SET  status=1\n" +
			" WHERE id in (${ids})")
	boolean clearCarStatus(@Param("ids") String ids);

	@Update("UPDATE outbound_equipment\n" +
			" SET status=1 \n" +
			" WHERE id in (${ids})")
	boolean clearEquipStatus(@Param("ids") String ids);

	@Update("UPDATE outbound_equipment\n" +
			" SET status=1 \n" +
			" WHERE card in (${ids})")
	boolean clearEquipStatusByCard(@Param("ids") String ids);
}
