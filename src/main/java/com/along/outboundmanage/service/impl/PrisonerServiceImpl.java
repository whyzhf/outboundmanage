package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.EquipmentDao;
import com.along.outboundmanage.dao.PrisonerDao;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPrisoner;
import com.along.outboundmanage.service.PrisonerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PrisonerServiceImpl implements PrisonerService {
    @Resource
    private PrisonerDao PrisonerDao;
    @Resource
    private EquipmentDao equipmentDao;
    @Override
    public OutboundPrisoner addPrisoner(OutboundPrisoner outboundPrisoner) {
         int id= 0;
         id= PrisonerDao.addPrisoner(outboundPrisoner);
         if(id>0){
             if(null!=outboundPrisoner.getEquipmentId()){
                 //修改设备状态为使用
                 equipmentDao.upEquipmentTypeAndStatus(0,0,outboundPrisoner.getEquipmentId()+"");
             }
         }else{
             outboundPrisoner=null;

         }
        return outboundPrisoner;
    }

    @Override
    public boolean upPrisoner(OutboundPrisoner outboundPrisoner) {
        String equipId=getEquipmentID(outboundPrisoner.getId()+"");
        if(PrisonerDao.upPrisoner(outboundPrisoner)) {
            if (outboundPrisoner.equals(outboundPrisoner.getEquipmentId() + "")) {

            } else {
                //原来绑定的改为未使用
                equipmentDao.upEquipmentTypeAndStatus(0, 1, equipId);
                //新绑定的改为使用中
                equipmentDao.upEquipmentTypeAndStatus(0, 0, outboundPrisoner.getEquipmentId() + "");
            }
            return true;
        }else{
            return false;
        }

    }


    @Override
    public boolean delPrisoner(String id) {
        String equipId = getEquipmentID(id);
        if (PrisonerDao.delPrisoner(id)) {
            //修改对应设备为未使用
            equipmentDao.upEquipmentTypeAndStatus(0, 1,equipId);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public List<OutboundPrisoner> getAllPrisoner(int areaId) {
        return PrisonerDao.getAllPrisoner(areaId);
    }

    @Override
    public List<OutboundPrisoner> getPrisonerByIds(String id) {
        return PrisonerDao.getPrisonerByIds(id);
    }
    //获取已绑定的设备id
    public String getEquipmentID(String ids){
        List<OutboundPrisoner> prisonerList= PrisonerDao.getPrisonerByIds(ids);
        List<String> list = prisonerList.stream().map(e->e.getEquipmentId()+"").collect(Collectors.toList());
        String str=String.join(",",  list);
        return str;
    }


}
