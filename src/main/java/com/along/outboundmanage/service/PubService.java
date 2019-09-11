package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPrisoner;

import java.util.List;

public interface PubService {

	List<OutboundPolice> getPolices(String time, String areaId);
	List<OutboundEquipment> getWatchs(String time, String areaId);
	List<OutboundEquipment> getHandsetIds(String time, String areaId);
	List<OutboundPrisoner> getPrisoners(String time, String areaId);
	List<OutboundEquipment> getGrapplersIds(String time, String areaId);
}
