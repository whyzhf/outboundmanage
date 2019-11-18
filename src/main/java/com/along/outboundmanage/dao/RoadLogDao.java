package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import com.along.outboundmanage.model.WSgpsData;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

public interface RoadLogDao {
	@Select(" SELECT id, route_id, equipment_id, `type`, longitude, latitude, lot, lat, speed, direction, form, uptime " +
			" FROM outbound_roadlog" +
			" WHERE route_id=#{routeId} " )
	List<OutboundRoute> getAllRoadlogByRouteId(@Param("routeId") int routeId);

	@Insert("INSERT INTO outbound_roadlog\n" +
			" (route_id, equipment_id, `type`, longitude, latitude, lot, lat, speed, direction, form, uptime)\n" +
			" VALUES(#{OutboundRoadlog.routeId}, #{OutboundRoadlog.equipmentId}, #{OutboundRoadlog.type},#{OutboundRoadlog.longitude}, #{OutboundRoadlog.latitude}," +
			" #{OutboundRoadlog.lot}, #{OutboundRoadlog.lat}, #{OutboundRoadlog.speed},#{OutboundRoadlog.direction},#{OutboundRoadlog.form}, " +
			" #{OutboundRoadlog.uptime})")
	boolean insertRoadLog(@Param("OutboundRoadlog") OutboundRoadlog OutboundRoadlog);


	@Delete("DELETE FROM outbound_roadlog WHERE route_id=#{id}")
	boolean delRoadLog(@Param("id") int id);

	@Select("SELECT  taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime,  longitude, latitude, speed, direction, color" +
			" FROM outbound_gpslog" +
			" WHERE taskId in (${taskId}) " +
			" GROUP BY equipCard, taskId, equip,  police, prisoner, stauts, errorStatus, uptime, longitude, latitude,speed, direction, color" +
			" Order BY uptime  ")
	List<WSgpsData> getGpsData(@Param("taskId") String taskId);

	@Select(" SELECT DISTINCT id from outbound_task where status=3 and area_id=${areaId}")
	List<Integer> getTaskIdByArea(@Param("areaId") String areaId);

}
