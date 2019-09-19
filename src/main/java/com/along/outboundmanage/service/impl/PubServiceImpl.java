package com.along.outboundmanage.service.impl;

import com.along.outboundmanage.dao.EquipRelManageDao;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;
import com.along.outboundmanage.model.OutboundPrisoner;
import com.along.outboundmanage.service.PubService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PubServiceImpl implements PubService {
	@Resource
	private EquipRelManageDao equipRelManageDao;

	@Override
	public List<OutboundPoliceForSel> getPolices(String time, String areaId, String taskId) {
		time=time.substring(0,10);
		return equipRelManageDao.getPolices(taskId,time,areaId);
	}

	@Override
	public List<OutboundEquipment> getWatchs(String time, String areaId,String taskId) {
		return getEquips(time,areaId,2,taskId);
	}

	public List<OutboundEquipment> getHandsetIds(String time, String areaId,String taskId) {
		return getEquips(time,areaId,1,taskId);
	}
	public List<OutboundEquipment> getEquips(String time, String areaId,int form,String taskId) {
		time=time.substring(0,10);
		String id="0";
		if (form==3) {
			List<OutboundPrisoner> prisoners = equipRelManageDao.getPrisoners(taskId,time, areaId);
			if (prisoners!=null && !prisoners.isEmpty()){
				List<String> list =new ArrayList<>();
				list = prisoners.stream().map(e -> e.getEquipmentId()+ "").distinct().collect(Collectors.toList());
				if (list!=null && !list.isEmpty()) {
					id = String.join(",", list);
				}
			}
			return equipRelManageDao.getAllEquipment(Integer.valueOf(areaId),form, id);
		}

		List<OutboundPoliceForSel> polices = equipRelManageDao.getPolices(taskId,time, areaId);

		if (polices!=null && !polices.isEmpty()){
			List<String> list =new ArrayList<>();
			if (form==1) {
				list = polices.stream().map(e -> e.getEquipmentId() + "").distinct().collect(Collectors.toList());
			}else {
				list = polices.stream().map(e -> e.getEquipmentId2() + "").distinct().collect(Collectors.toList());
			}
			if (list!=null && !list.isEmpty()) {
				id = String.join(",", list);
			}
		}
		return equipRelManageDao.getAllEquipment(Integer.valueOf(areaId),form, id);
	}
	@Override
	public List<OutboundPrisoner> getPrisoners(String time, String areaId,String taskId) {
		time=time.substring(0,10);
		return equipRelManageDao.getPrisoners(taskId,time, areaId);
	}

	@Override
	public List<OutboundEquipment> getGrapplersIds(String time, String areaId,String taskId) {
		return getEquips(time,areaId,0,taskId);
	}
}
