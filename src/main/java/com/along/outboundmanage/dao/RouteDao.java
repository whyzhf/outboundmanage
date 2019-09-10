package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundRoute;

import com.along.outboundmanage.model.OutboundRouteJson;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RouteDao {
	@Select(" SELECT id, name, uptime, distance, origin, destination, area_id,rail" +
			" FROM outbound_route" +
			" WHERE area_id=#{areaId}" )
	List<OutboundRouteJson> getAllRouteByAreaId(@Param("areaId") int areaId);

	@UpdateProvider(type = SqlProvider.class, method = "updataRoute")
	boolean updateRouteById(@Param("OutboundRoute") OutboundRoute outboundRoute);

	@Insert("INSERT INTO outbound_route" +
			" (  name, uptime, distance, origin, destination, area_id,rail)" +
			" VALUES(#{OutboundRoute.name},#{OutboundRoute.uptime},#{OutboundRoute.distance}, " +
			"#{OutboundRoute.origin},#{OutboundRoute.destination},#{OutboundRoute.areaId},#{OutboundRoute.rail})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundRoute.id", keyColumn = "id")
	int insertRoute(@Param("OutboundRoute") OutboundRoute outboundRoute);


	@Delete("delete from outbound_route where id in (${ids})")
	boolean delRoute(@Param("ids") String ids);


}
