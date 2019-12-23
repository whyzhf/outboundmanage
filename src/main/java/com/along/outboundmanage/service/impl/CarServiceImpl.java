package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.CarDao;
import com.along.outboundmanage.model.OutboundCar;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.service.CarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CarServiceImpl implements CarService {
    @Resource
    private CarDao CarDao;

    @Override
    public boolean checCar(OutboundCar outboundPolice) {
        String card=outboundPolice.getCard();
        Integer policeId = CarDao.checCar(outboundPolice.getCard());
        if (null==outboundPolice.getId()&&policeId!=null){//新增
            return false;
        }else if (policeId!=null&& !policeId.equals(outboundPolice.getId())){//修改
            return false;
        }
        return true;
    }
    @Override
    public OutboundCar addCar(OutboundCar outboundCar) {
       /* Integer policeUserId = CarDao.checCar(outboundCar.getCard());
        if(policeUserId!=null){//该card已存在
            return null;
        }
*/
         int id= 0;

         id= CarDao.addCar(outboundCar);
         if(id>0){

         }else{
             outboundCar=null;

         }
        return outboundCar;
    }

    @Override
    public boolean upCar(OutboundCar outboundCar ) {
       /* Integer policeUserId = CarDao.checCar(outboundCar.getCard());
        if(policeUserId!=null && policeUserId!=outboundCar.getId()){//该card已存在
            return false;
        }*/
        return CarDao.upCar(outboundCar);
    }


    @Override
    public boolean delCar(String id) {
        return CarDao.delCar(id);
    }

    @Override
    public List<OutboundCar> getAllCar(int areaId) {
        return CarDao.getAllCar(areaId);
    }
    @Override
    public List<OutboundCar> getCarAble(int areaId) {
        return CarDao.getCarAble(areaId);
    }
}
