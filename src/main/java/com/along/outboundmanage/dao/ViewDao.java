package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.CountByCity;
import com.along.outboundmanage.model.CountTypeByCity;
import com.along.outboundmanage.model.PCount;
import com.along.outboundmanage.model.ViewCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface ViewDao {
	//查询某天总人数 time = 'yyyy-MM-dd 23:59:59'
	@Select("SELECT count(id)  \n" +
			" FROM outbound_prisoner WHERE   intime <= '${time}' and (outtime >= '${time}' or outtime is null) "+"  ${area}")
	int getCountByDate(@Param("time") String time, @Param("area")String area);

	/*//查询某月内每天新增总人数
	@SelectProvider(type = SqlProvider.class, method = "getDate")
	List<PCount> getCount(@Param("area")String area);*/
	//查询上月内每天新增总人数
	@SelectProvider(type = SqlProvider.class, method = "getAddDate")
	List<PCount> getAddDate(@Param("area")String area);

	/** 查询某时间状态占比：
	 *                  天：${time}= 'and start_time like '2019-09-09%'
	 *                  月：${time}= 'and start_time like '2019-09%'
	 *                  省厅：  ${area}= ''
	 *                  地级市：${area}= 'and area_id in(1800)'
	 *                  状态： type in ();
	 */
	@Select("select   t.type as type,count(t.pid) as count,t.times as data from (\n" +
			" select   t.id,`type`, tp.prisoner_id as pid,convert(start_time,date) as times from outbound_task t\n" +
			" left join outbound_task_prisoner_rel tp on t.id=tp.task_id\n" +
			" where  status=4 and start_time like '${time}' " +
			"  ${type}"+
			"  ${area}"+
			" and  tp.prisoner_id is not null\n" +
			" ) t\n" +
			" group by  t.type,t.times\n" +
			" order by t.times,  t.type")
	List<ViewCount> getDateByData(@Param("time") String time,@Param("type") String type, @Param("area")String area);

	/** 查询某时间外出总人数：
	 *                  天：${time}= 'and start_time like '2019-09-09%'
	 *                  月：${time}= 'and start_time like '2019-09%'
	 *                  省厅：  ${area}= ''
	 *                  地级市：${area}= 'and area_id in(1800)'
	 *                  状态： type in ();
	 */
	@Select("select   count(t.pid) as count ,t.times as  data from (\n" +
			" select   t.id,`type`, tp.prisoner_id as pid,convert(start_time,date) as times from outbound_task t\n" +
			" left join outbound_task_prisoner_rel tp on t.id=tp.task_id\n" +
			" where  status=4 and start_time like '${time}' " +
			"  ${area}"+
			" and  tp.prisoner_id is not null\n" +
			" ) t\n" +
			" group by t.times\n" +
			" order by t.times\n")
	List<ViewCount> getCountDataByData(@Param("time") String time,@Param("type") String type, @Param("area")String area);

	//获取各地级市总人数
	@Select("select count(p.id) as count ,a.id as areaId, a.name as name\n" +
			" from (\n" +
			"  SELECT p.id as id ,a.par_id as area_id\n" +
			"  FROM outbound_prisoner p\n" +
			"  left join outbound_area a on a.id=p.area_id\n" +
			"   WHERE intime <= concat(current_date,' 23:59:59') and (outtime >= concat(current_date,' 23:59:59') or outtime is null)  and a.par_id is not null" +
			" ) p\n" +
			" left join outbound_area a on a.id=p.area_id\n" +
			" group by a.id")
	List<CountByCity> getCountByCity();

	//获取某个地级市各个监所总人数
	@Select("select count(p.id) as count ,a.id as areaId, a.name as name\n" +
			" from (\n" +
			"  SELECT p.id as id ,a.id as area_id\n" +
			"  FROM outbound_prisoner p\n" +
			"  left join outbound_area a on a.id=p.area_id\n" +
			"   WHERE intime <= concat(current_date,' 23:59:59') and (outtime >= concat(current_date,' 23:59:59') or outtime is null)  and a.par_id=#{areaId}" +
			" ) p\n" +
			" left join outbound_area a on a.id=p.area_id\n" +
			" group by a.id")
	List<CountByCity> getSecCountByCity(@Param("areaId")int areaId);

	//获取某个监所总人数
	@Select("SELECT count(p.id) as count  ,a.id as areaId ,a.name as name\n" +
			" FROM outbound_prisoner p\n" +
			" left join outbound_area a on a.id=p.area_id\n" +
			"  WHERE intime <= concat(current_date,' 23:59:59') and (outtime >= concat(current_date,' 23:59:59') or outtime is null)  and a.id=#{areaId}")
	List<CountByCity> getThrCountByCity(@Param("areaId")int areaId);

	//获取各地级市状态
	@Select("select group_concat(m.type separator '&') as type ,m.name,m.areaId " +
			"from ( " +
			" select CONCAT_WS(':',t.type,count(t.pid))as type ,a.name,a.id as areaId\n" +
			"   from (\n" +
			"   select t.id,t.type, tp.prisoner_id as pid,a.par_id as area_id  \n" +
			"   from outbound_task t \n" +
			"   left join outbound_task_prisoner_rel tp on t.id=tp.task_id \n" +
			"   left join outbound_area a on a.id=t.area_id\n" +
			"   where status=4 and start_time like '${time}%'  and tp.prisoner_id is not null  \n" +
			" ) t\n" +
			"   left join outbound_area a on a.id=t.area_id\n" +
			"   group by t.type" +
			"   order by t.type " +
			") m")
	List<CountTypeByCity> getTypeByCity(@Param("time")String time);

	//获取某个地级市各个监所状态
	@Select("select group_concat(m.type separator '&') as type ,m.name,m.areaId  " +
			" from ( " +
			"  select CONCAT_WS(':',t.type,count(t.pid))as type ,a.name,a.id as areaId\n" +
			"  from (\n" +
			"   select t.id,t.type, tp.prisoner_id as pid,a.id as area_id\n" +
			"   from outbound_task t \n" +
			"   left join outbound_task_prisoner_rel tp on t.id=tp.task_id \n" +
			"   left join outbound_area a on a.id=t.area_id\n" +
			"   where status=4 and start_time like '${time}%'  and tp.prisoner_id is not null  and a.par_id=#{areaId}\n" +
			" ) t\n" +
			"   left join outbound_area a on a.id=t.area_id\n" +
			" group by t.type order by  t.type" +
			")m ")
	List<CountTypeByCity> getSecTypeByCity(@Param("time")String time, @Param("areaId")int areaId);

	//获取某个监所状态
	@Select("select t.type as type,count(t.pid) as count,a.name,a.id as areaId" +
			"   from outbound_task t \n" +
			"   left join outbound_task_prisoner_rel tp on t.id=tp.task_id \n" +
			"   left join outbound_area a on a.id=t.area_id\n" +
			"   where status=4 and start_time like '${time}%'  and tp.prisoner_id is not null  and a.id=#{areaId}\n" +
			" group by t.type,t.times order by t.times, t.type ")
	List<CountTypeByCity> getThrTypeByCity(@Param("time")String time, @Param("areaId")int areaId);
}
