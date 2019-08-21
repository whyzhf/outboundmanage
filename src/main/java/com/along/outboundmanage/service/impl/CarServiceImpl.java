package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.CarDao;
import com.along.outboundmanage.model.OutboundCar;
import com.along.outboundmanage.service.CarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CarServiceImpl implements CarService {
    @Resource
    private CarDao CarDao;
    @Override
    public OutboundCar addCar(OutboundCar outboundCar) {
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


}
