package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.EquipmentDao;
import com.along.outboundmanage.dao.PoliceDao;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;
import com.along.outboundmanage.service.EquipmentService;
import com.along.outboundmanage.service.PoliceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Resource
    private EquipmentDao equipmentDao;
    @Override
    public OutboundEquipment addEquipment(OutboundEquipment outboundPolice) {
         int id= 0;
         id= equipmentDao.addEquipment(outboundPolice);
         if(id>0){

         }else{
             outboundPolice=null;

         }
        return outboundPolice;
    }

    @Override
    public boolean upEquipment(OutboundEquipment outboundPolice) {
        return equipmentDao.upEquipment(outboundPolice);
    }

    @Override
    public boolean delEquipment(String id) {
        return equipmentDao.delEquipment(id);
    }

    @Override
    public List<OutboundEquipment> getAllEquipment(int areaId) {
        return equipmentDao.getAllEquipment(areaId);
    }

    @Override
    public boolean upEquipmentTypeAndStatus(int type, int status, String id) {
        return equipmentDao.upEquipmentTypeAndStatus(type,status,id);
    }

    @Override
    public List<OutboundEquipment> getAllEquipmentByForm(int areaId, String form) {
        return equipmentDao.getAllEquipmentByForm(areaId,form);
    }


}
