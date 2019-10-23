package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.AreaDao;
import com.along.outboundmanage.model.OutboundArea;
import com.along.outboundmanage.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaDao areaDao;
    @Override
    public Map<String, Object> getAllArea() {
        return getTree(areaDao.getAllArea());
    }

    @Override
    public List<Integer> getAreaIdsByPar(int areaId) {
        return areaDao.getAreaIdsByPar(areaId);
    }

    @Override
    public List<OutboundArea> getAreaDesc(int areaId) {
        return areaDao.getAreaDesc(areaId);
    }

    public Map<String, Object> getTree(List<OutboundArea> list) {
        Map<String, Object> resmap=new HashMap<>();
        Map<Integer, OutboundArea> map;
        List<OutboundArea> treelist= new ArrayList<>();

        if (null==list||list.isEmpty()){
            return null;
        }
        map = list.stream().collect(Collectors.toMap(OutboundArea::getId, a -> a,(k1, k2)->k1));
        // 将list集合对象转换为json的字符串
        // 如果id是父级的话就放入tree中treelist
        for (OutboundArea menu : list) {

            if (null==map.get(menu.getParId())) {

                treelist.add(menu);
            } else {
                // 子级通过父id获取到父级的类型
                OutboundArea parent = map.get(menu.getParId());
                // 父级获得子级，再将子级放到对应的父级中
                parent.addChildren(menu);
            }
        }
        resmap.put("data",treelist);
        return resmap;
    }
}
