package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import org.apache.ibatis.annotations.*;

import java.util.List;

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


}
