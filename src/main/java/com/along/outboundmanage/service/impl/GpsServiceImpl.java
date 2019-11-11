package com.along.outboundmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.dao.RoadLogDao;
import com.along.outboundmanage.model.HisGpsData;
import com.along.outboundmanage.model.HistData;
import com.along.outboundmanage.model.WSgpsData;
import com.along.outboundmanage.service.GpsService;
import com.along.outboundmanage.utill.GpsUtil.GetData;
import com.along.outboundmanage.utill.JedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.StringToLong;
import static com.along.outboundmanage.utill.GpsUtil.FileUtil.getData;
import static com.along.outboundmanage.utill.GpsUtil.GetData.savaRedis;

@Service
public class GpsServiceImpl implements GpsService {
	@Resource
	private RoadLogDao roadLogDao;
	@Override
	public List<HistData> getfile(String taskId) {
		try {
			//	System.out.println("1111111");
			return getData(taskId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	/**
	 * 获取正在执行任务数据
	 */
	@Override
	public Map<String, Object> getGpsData(String taskId) {
		//先从redis取数据
		Map<String, Object> gpsData = GetData.getGpsData(taskId);
		if (gpsData!=null && !gpsData.isEmpty()){
			return  gpsData;
		}else{//redis无数据，再从数据库取数据,并写入redis
			taskId=taskId.split("R")[0];//测试用，删除此行
			List<WSgpsData> gpsData1 = roadLogDao.getGpsData(taskId);
			Map<String, List<WSgpsData>> map = gpsData1.stream().collect(Collectors.groupingBy(WSgpsData::getEquipCard));
			//System.out.println(map);
			gpsData=new HashMap<>();
			gpsData.put("data",map);
			savaRedis(gpsData1);
			return gpsData;
		}
	}



	/**
	 * 获取历史任务数据
	 */
	@Override
	public Map<String, Object> getHisGpsData(String taskId) {
		//先从redis取数据
		Map<String, Object> gpsData = GetData.getHisGpsData(taskId);
		if (gpsData!=null && !gpsData.isEmpty()){
			return  gpsData;
		}else{//redis无数据，再从数据库取数据,并写入redis
			taskId=taskId.split("R")[0];//测试用，删除此行
			List<WSgpsData> gpsData1 = roadLogDao.getGpsData(taskId);
			Map<String, HisGpsData> gpsMap =new HashMap<>();
			for (WSgpsData key : gpsData1) {
				if(gpsMap.get(key.getEquipCard())!=null){
					gpsMap.get(key.getEquipCard()).getGpsData().add(new BigDecimal[]{key.getLongitude(),key.getLatitude()});
				}else{
					List<BigDecimal[]> gpslist=new ArrayList<>();
					gpslist.add(new BigDecimal[]{key.getLongitude(),key.getLatitude()});
					gpsMap.put(key.getEquipCard(),new HisGpsData.Builder().color(key.getColor())
																	.equipCard(key.getEquipCard())
																	.prisoner(key.getPrisoner())
																	.police(key.getPolice())
																	.gpsData(gpslist).build()
															);
				}

			}
			List<HisGpsData>gpsDataList=new ArrayList<>();
			gpsMap.forEach((K,V)->gpsDataList.add(V));
			//System.out.println(map);
			gpsData=new HashMap<>();
			gpsData.put("data",gpsDataList);
			//写入redis
			savaRedis(gpsData1);
			return gpsData;
		}
	}
}
