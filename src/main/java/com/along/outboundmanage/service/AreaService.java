package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundArea;
import com.along.outboundmanage.model.OutboundMenu;
import com.along.outboundmanage.model.OutboundSession;
import com.along.outboundmanage.model.OutboundUser;

import java.util.List;
import java.util.Map;

public interface AreaService {

	//地域树
	Map<String, Object> getAllArea();

	List<Integer> getAreaIdsByPar( int areaId);

	List<OutboundArea> getAreaDesc(int areaId);
}
