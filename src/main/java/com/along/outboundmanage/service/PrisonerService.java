package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPrisoner;

import java.util.List;

public interface PrisonerService {
	boolean checPrisoner(OutboundPrisoner outboundPrisoner);
	//新增
	OutboundPrisoner addPrisoner(OutboundPrisoner outboundPrisoner);
	//修改
	boolean upPrisoner(OutboundPrisoner outboundPrisoner);
	//删除
	boolean delPrisoner(String id);


	//查询
	List<OutboundPrisoner> getAllPrisoner(int areaId);
	//根据id查询
	List<OutboundPrisoner> getPrisonerByIds( String id);


}
