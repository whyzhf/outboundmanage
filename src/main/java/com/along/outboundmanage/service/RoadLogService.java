package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;

import java.util.List;

public interface RoadLogService {


	//存储路线（gps位置）
	boolean saveGpsRoute(OutboundRoadlog outboundRoadlog);
	//存储围栏（gps位置）
	boolean saveFence(OutboundRoadlog outboundRoadlog);
	//清除记录（gps位置）
	boolean delRoadLog(int id);
}
