package com.along.outboundmanage.service;

import com.along.outboundmanage.model.HistData;
import com.along.outboundmanage.model.WSgpsData;

import java.util.List;
import java.util.Map;

public interface GpsService {
	List<HistData> getfile(String taskId);

	Map<String, Object> getGpsData(String taskId);

	Map<String, Object> getHisGpsData(String taskId);
}
