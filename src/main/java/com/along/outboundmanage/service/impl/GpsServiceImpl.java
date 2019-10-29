package com.along.outboundmanage.service.impl;

import com.along.outboundmanage.model.HistData;
import com.along.outboundmanage.service.GpsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.along.outboundmanage.utill.GpsUtil.FileUtil.getData;

@Service
public class GpsServiceImpl implements GpsService {
	@Override
	public List<HistData> getfile(String taskId) {
		try {
			//	System.out.println("1111111");
			return getData(taskId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
