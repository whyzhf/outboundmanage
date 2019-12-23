package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;

import java.util.List;

public interface PoliceService {

	//新增
	OutboundPoliceForSel addPolice(OutboundPolice outboundPolice);
	//修改
	boolean checPolice( OutboundPolice outboundPolice);
	//修改
	boolean upPolice( OutboundPolice outboundPolice);
	//删除
	boolean delPolice( String id);
	//查询
	List<OutboundPoliceForSel> getAllPolice(int areaId);
	//根据ID查询
	List<OutboundPolice> getPoliceByIds( String ids);

}
