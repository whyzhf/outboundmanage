package com.along.outboundmanage.service.impl;

import com.along.outboundmanage.dao.EquipRelManageDao;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPrisoner;
import com.along.outboundmanage.service.PubService;
import net.sf.jsqlparser.statement.select.Join;
import org.springframework.cache.annotation.Cacheable;
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
	public List<OutboundPolice> getPolices(String time, String areaId) {
		time=time.substring(0,10);
		return equipRelManageDao.getPolices(time,areaId);
	}

	@Override
	public List<OutboundEquipment> getWatchs(String time, String areaId) {
		return getEquips(time,areaId,2);
	}

	public List<OutboundEquipment> getHandsetIds(String time, String areaId) {
		return getEquips(time,areaId,1);
	}
	public List<OutboundEquipment> getEquips(String time, String areaId,int form) {

		String id="0";
		if (form==3) {
			List<OutboundPrisoner> prisoners = equipRelManageDao.getPrisoners(time, areaId);
			if (prisoners!=null && !prisoners.isEmpty()){
				List<String> list =new ArrayList<>();
				list = prisoners.stream().map(e -> e.getEquipmentId() + "").collect(Collectors.toList());
				if (list!=null && !list.isEmpty()) {
					id = String.join(",", list);
				}
			}
			return equipRelManageDao.getAllEquipment(Integer.valueOf(areaId),form, id);
		}

		List<OutboundPolice> polices = equipRelManageDao.getPolices(time, areaId);

		if (polices!=null && !polices.isEmpty()){
			List<String> list =new ArrayList<>();
			if (form==1) {
				list = polices.stream().map(e -> e.getEquipmentId() + "").collect(Collectors.toList());
			}else {
				list = polices.stream().map(e -> e.getEquipmentId2() + "").collect(Collectors.toList());
			}
			if (list!=null && !list.isEmpty()) {
				id = String.join(",", list);
			}
		}
		return equipRelManageDao.getAllEquipment(Integer.valueOf(areaId),form, id);
	}
	@Override
	public List<OutboundPrisoner> getPrisoners(String time, String areaId) {
		return equipRelManageDao.getPrisoners(time, areaId);
	}

	@Override
	public List<OutboundEquipment> getGrapplersIds(String time, String areaId) {
		return getEquips(time,areaId,3);
	}
}
