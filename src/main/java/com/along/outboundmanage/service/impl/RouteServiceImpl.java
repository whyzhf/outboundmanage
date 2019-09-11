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
import java.util.List;


@Service
public class RouteServiceImpl implements RouteService {
    @Resource
    private RouteDao routeDao;


    @Override
    public List<OutboundRouteJson> getAllRoute(int areaId) {
        return routeDao.getAllRouteByAreaId(areaId);
    }

    @Override
    public boolean updateRouteById(OutboundRoute outboundRoute) {
        return routeDao.updateRouteById(outboundRoute);
    }

    @Override
    public boolean delRoute(String ids) {
        return routeDao.delRoute(ids);
    }

    @Override
    public OutboundRoute insertRoute(OutboundRoute outboundRoute) {
        int id= 0;
        id= routeDao.insertRoute(outboundRoute);
        if(id>0){//新增成功

        }else{
            outboundRoute=null;
        }
        return outboundRoute;
    }

    @Override
    public String getOd(int id) {
        return routeDao.getOd(id);
    }


}
