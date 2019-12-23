package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundEquipment;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EquipmentService {
	//新增
	OutboundEquipment addEquipment(OutboundEquipment OutboundEquipment);
	//修改
	boolean checEquipment( OutboundEquipment OutboundEquipment);
	//修改
	boolean upEquipment( OutboundEquipment OutboundEquipment);
	//删除
	boolean delEquipment(String id);
	//查询
	List<OutboundEquipment> getAllEquipment(int areaId);

	//修改
	boolean upEquipmentTypeAndStatus( int type,int status, String id);

	//查询可使用的设备
	List<OutboundEquipment> getAllEquipmentByForm(int areaId, String form);
}
