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
    public boolean checPrisoner(OutboundPrisoner outboundPolice) {
        String card=outboundPolice.getCard();
        Integer policeId = PrisonerDao.checPrisoner(outboundPolice.getCard());
        if (null==outboundPolice.getId()&&policeId!=null){//新增
            return false;
        }else if (policeId!=null&& !policeId.equals(outboundPolice.getId())){//修改
            return false;
        }
        return true;
    }

    @Override
    public OutboundPrisoner addPrisoner(OutboundPrisoner outboundPrisoner) {
         int id= 0;
        String card=outboundPrisoner.getCard();
       /* Integer policeUserId = PrisonerDao.checPrisoner(card);
        if(policeUserId!=null){//该card已存在
            return null;
        }*/
         id= PrisonerDao.addPrisoner(outboundPrisoner);
         if(id>0){
             if(null!=outboundPrisoner.getEquipmentId()){
                 //修改设备状态为使用
                 equipmentDao.upEquipmentTypeAndStatus(0,0,outboundPrisoner.getEquipmentId()+"");
             }
         }else{
             outboundPrisoner=null;

         }
        List<OutboundPrisoner> allPrisonerById = PrisonerDao.getAllPrisonerById(outboundPrisoner.getId() + "");
        return allPrisonerById.get(0);
    }

    @Override
    public boolean upPrisoner(OutboundPrisoner outboundPrisoner) {
       /* Integer policeUserId = PrisonerDao.checPrisoner(outboundPrisoner.getCard());
        if(policeUserId!=null&&policeUserId!=outboundPrisoner.getId()){//该card已存在
            return false;
        }*/
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
