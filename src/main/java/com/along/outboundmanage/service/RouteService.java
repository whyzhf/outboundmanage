package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import com.along.outboundmanage.model.OutboundRouteJson;
import com.along.outboundmanage.model.OutboundTask;

import java.util.List;

public interface RouteService {

	//查询
	List<OutboundRouteJson> getAllRoute(int areaId);
	//修改
	boolean updateRouteById(OutboundRoute outboundRoute);
	//删除
	boolean delRoute(String ids);
	//新增
	OutboundRoute insertRoute(OutboundRoute outboundRoute);
	String getOd( int id);
}
