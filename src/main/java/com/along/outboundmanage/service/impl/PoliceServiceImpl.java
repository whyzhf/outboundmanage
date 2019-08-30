package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.EquipmentDao;
import com.along.outboundmanage.dao.PoliceDao;
import com.along.outboundmanage.dao.UserDao;
import com.along.outboundmanage.model.*;

import com.along.outboundmanage.service.PoliceService;
import com.along.outboundmanage.utill.MD5Util;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PoliceServiceImpl implements PoliceService {
    @Resource
    private UserDao userDao;
    @Resource
    private PoliceDao policeDao;
    @Resource
    private EquipmentDao equipmentDao;
    @Override
    public OutboundPoliceForSel addPolice(OutboundPolice outboundPolice) {
         int id= 0;
         String card=outboundPolice.getCard();
         Integer userId= userDao.checkCard(card);
         if(userId!=null){//user表存在
            /* Integer userP=policeDao.findPoliceUserId(card);
                if(userP!=null){//police表存在

                }else{

                }*/
         }else{
             OutboundUser use=new OutboundUser();
             use.setAreaId(outboundPolice.getAreaId());
             use.setCard(outboundPolice.getCard());
             use.setUserName(outboundPolice.getCard());
             use.setTrueName(outboundPolice.getName());
             use.setPassword(MD5Util.MD5("123456"));
             int userid=userDao.insertUser(use);
             userId=use.getId();
             userDao.addUserRole(userId,5);

         }
        outboundPolice.setUserId(userId);
         id= policeDao.addPolice(outboundPolice);
         if(id>0){
             if(null!=outboundPolice.getEquipmentId()|| null!=outboundPolice.getEquipmentId2()){
                 String equipId=(outboundPolice.getEquipmentId()!=null?outboundPolice.getEquipmentId():"0")+","+(outboundPolice.getEquipmentId2()!=null?outboundPolice.getEquipmentId2():"0");
                 //修改设备状态为使用
                 equipmentDao.upEquipmentTypeAndStatus(0,0,equipId);
             }
         }else{
             outboundPolice=null;

         }
       // policeDao.getAllPoliceById(outboundPolice.getId()+"");
        return  policeDao.getAllPoliceById(outboundPolice.getId()+"").get(0);
    }

    @Override
    public boolean upPolice(OutboundPolice outboundPolice) {
        String equipId=getEquipmentID(outboundPolice.getId()+"");
        String newEquipId=(outboundPolice.getEquipmentId()!=null?outboundPolice.getEquipmentId():"0")+","+(outboundPolice.getEquipmentId2()!=null?outboundPolice.getEquipmentId2():"0");
        System.out.println(outboundPolice);
        if(policeDao.upPolice(outboundPolice)){
            if (equipId.equals(newEquipId)){

            } else {
                //原来绑定的改为未使用
                equipmentDao.upEquipmentTypeAndStatus(0, 1, equipId);
                //新绑定的改为使用中
                equipmentDao.upEquipmentTypeAndStatus(0, 0, newEquipId);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delPolice(String id) {
        String equipId=getEquipmentID(id);
        if(policeDao.delPolice(id)){
            //原来绑定的改为未使用
            equipmentDao.upEquipmentTypeAndStatus(0, 1, equipId);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<OutboundPoliceForSel> getAllPolice(int areaId) {
        return policeDao.getAllPolice( areaId);
    }

    @Override
    public List<OutboundPolice> getPoliceByIds(String ids) {
        return policeDao.getPoliceByIds(ids);
    }
    //获取已绑定的设备id
    public String getEquipmentID(String ids){
        List<OutboundPolice> prisonerList= getPoliceByIds(ids);
        List<String> list = prisonerList.stream().map(e->
            (e.getEquipmentId()!=null?e.getEquipmentId():"0")+","+(e.getEquipmentId2()!=null?e.getEquipmentId2():"0")
        ).collect(Collectors.toList());
        String str=String.join(",",  list);
        return str;
    }
}
