package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundCar;

import java.util.List;

public interface CarService {
	//新增
	boolean checCar(OutboundCar outboundCar);
	//新增
	OutboundCar addCar(OutboundCar outboundCar);
	//修改
	boolean upCar(OutboundCar outboundCar);
	//删除
	boolean delCar(String id);
	//查询
	List<OutboundCar> getAllCar(int areaId);
	//可用车辆
	List<OutboundCar> getCarAble(int areaId);
}
