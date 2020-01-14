package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.RoadLogDao;
import com.along.outboundmanage.dao.RouteDao;
import com.along.outboundmanage.dao.TaskDao;
import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.model.OutboundRoute;
import com.along.outboundmanage.model.OutboundRouteJson;
import com.along.outboundmanage.model.OutboundTask;
import com.along.outboundmanage.service.RouteService;
import com.along.outboundmanage.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.along.outboundmanage.utill.GeneralUtils.getJsonArr;


@Service
public class RouteServiceImpl implements RouteService {
    @Resource
    private RouteDao routeDao;


    @Override
    public List<OutboundRouteJson> getAllRoute(int areaId) {
        List<OutboundRouteJson> dataList=new ArrayList<>();
        OutboundRouteJson orj=null;
        String[]strArr=new String[2];
        double[]OriginLngLat=new double[2];
        double[]DestinationLngLat=new double[2];
        List<OutboundRoute> allRouteByAreaId = routeDao.getAllRouteByAreaId(areaId);
        if(null!=allRouteByAreaId&&!allRouteByAreaId.isEmpty()){
            for (OutboundRoute e : allRouteByAreaId) {
	            orj=new OutboundRouteJson();
                orj.setId(e.getId());
                orj.setName(e.getName());
                orj.setAreaId(e.getAreaId());
                orj.setOrigin(e.getOrigin());
                orj.setDestination(e.getDestination());
                strArr = e.getOriginLngLat().split(",");
                OriginLngLat[0]=Double.parseDouble(strArr[0]);
                OriginLngLat[1]=Double.parseDouble(strArr[1]);
                orj.setOriginLngLat(OriginLngLat);
                strArr = e.getDestinationLngLat().split(",");
                DestinationLngLat[0]=Double.parseDouble(strArr[0]);
                DestinationLngLat[1]=Double.parseDouble(strArr[1]);
                orj.setDestinationLngLat(DestinationLngLat);
                orj.setDistance(e.getDistance());
                orj.setRail(getJsonArr(e.getRail()));
                orj.setUptime(e.getUptime());
                dataList.add(orj);
            }
        }
        return dataList;
    }

    @Override
    public OutboundRouteJson getRouteById(int id) {
        OutboundRoute e = routeDao.getRouteById(id);
        OutboundRouteJson orj=new OutboundRouteJson();
        if(null!=orj) {
            String[] strArr = new String[2];
            double[] OriginLngLat = new double[2];
            double[] DestinationLngLat = new double[2];
            orj.setId(e.getId());
            orj.setName(e.getName());
            orj.setAreaId(e.getAreaId());
            orj.setOrigin(e.getOrigin());
            orj.setDestination(e.getDestination());
            strArr = e.getOriginLngLat().split(",");
            if(strArr.length>0) {
                OriginLngLat[0] = Double.parseDouble(strArr[0]);
                OriginLngLat[1] = Double.parseDouble(strArr[1]);
            }
            orj.setOriginLngLat(OriginLngLat);
            strArr = e.getDestinationLngLat().split(",");
            if(strArr.length>0) {
                DestinationLngLat[0] = Double.parseDouble(strArr[0]);
                DestinationLngLat[1] = Double.parseDouble(strArr[1]);
            }
            orj.setDestinationLngLat(DestinationLngLat);
            orj.setDistance(e.getDistance());
            orj.setRail(getJsonArr(e.getRail()));
            orj.setUptime(e.getUptime());
        }
        return orj;
    }

    @Override
    public boolean updateRouteById(OutboundRouteJson e) {
        OutboundRoute orj=new OutboundRoute();
        orj.setId(e.getId());
        orj.setName(e.getName());
        orj.setAreaId(e.getAreaId());
        orj.setOrigin(e.getOrigin());
        orj.setDestination(e.getDestination());
        if(e.getOriginLngLat()!=null) {
            orj.setOriginLngLat(e.getOriginLngLat()[0] + "," + e.getOriginLngLat()[1]);
        }
        if(e.getDestinationLngLat()!=null) {
            orj.setDestinationLngLat(e.getDestinationLngLat()[0] + "," + e.getDestinationLngLat()[1]);
        }
        orj.setDistance(e.getDistance());
        orj.setRail(e.getRail());
        orj.setUptime(e.getUptime());
        return routeDao.updateRouteById(orj);
    }

    @Override
    public boolean delRoute(String ids) {
        return routeDao.delRoute(ids);
    }

    @Override
    public OutboundRoute insertRoute(OutboundRouteJson e) {
        int id= 0;
        OutboundRoute orj=new OutboundRoute();
        orj.setId(e.getId());
        orj.setName(e.getName());
        orj.setAreaId(e.getAreaId());
        orj.setOrigin(e.getOrigin());
        orj.setDestination(e.getDestination());
        if(e.getOriginLngLat()!=null) {
            orj.setOriginLngLat(e.getOriginLngLat()[0] + "," + e.getOriginLngLat()[1]);
        }
        if(e.getDestinationLngLat()!=null) {
            orj.setDestinationLngLat(e.getDestinationLngLat()[0] + "," + e.getDestinationLngLat()[1]);
        }
        orj.setDistance(e.getDistance());
        orj.setRail(e.getRail());
        orj.setUptime(e.getUptime());

        id= routeDao.insertRoute(orj);
        if(id>0){//新增成功

        }else{
            orj=null;
        }
        return orj;
    }

    @Override
    public String getOd(int id) {
        return routeDao.getOd(id);
    }


}
