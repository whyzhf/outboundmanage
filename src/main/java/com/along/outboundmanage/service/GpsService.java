package com.along.outboundmanage.service;

import com.along.outboundmanage.model.HistData;

import java.util.List;

public interface GpsService {
	List<HistData> getfile(String taskId);
}
