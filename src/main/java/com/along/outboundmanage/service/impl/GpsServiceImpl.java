package com.along.outboundmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.dao.RoadLogDao;
import com.along.outboundmanage.model.HisGpsData;
import com.along.outboundmanage.model.HistData;
import com.along.outboundmanage.model.WSgpsData;
import com.along.outboundmanage.service.GpsService;
import com.along.outboundmanage.utill.GpsUtil.FileUtil;
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
import static com.along.outboundmanage.utill.JdbcUtils.queryHisGpsList;

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
	/*@Override
	public Map<String, Object> getGpsData(String taskId) {
		//先从redis取数据
		Map<String, Object> gpsData = GetData.getGpsData(taskId);
		if (gpsData!=null && !gpsData.isEmpty()){
			gpsData.put("code",200);
			gpsData.put("message",200);
			return  gpsData;
		}else{//redis无数据，再从数据库取数据,并写入redis
			taskId=taskId.split("R")[0];//测试用，删除此行
			List<WSgpsData> gpsData1 = roadLogDao.getGpsData(taskId);
			if (gpsData1!=null && !gpsData1.isEmpty()){
				gpsData.put("code",4000);
				gpsData.put("message","not data");
				return null;
			}else {
				Map<String, List<WSgpsData>> map = gpsData1.stream().collect(Collectors.groupingBy(WSgpsData::getEquipCard));
				//System.out.println(map);
				gpsData = new HashMap<>();
				gpsData.put("code",200);
				gpsData.put("message",200);
				gpsData.put("data", map);
				savaRedis(gpsData1);
			}
			return gpsData;
		}
	}*/

	/**
	 * 获取正在执行任务数据
	 */
	@Override
	public Map<String, Object> getGpsData(String taskId) {
		Map<String, Object> resMap =new HashMap<>();
		Map<String, HisGpsData> gpsMap =new HashMap<>();
		List<HisGpsData>gpsDataList=new ArrayList<>();
		//先从redis取数据
		gpsMap = GetData.getHisGpsData(taskId);
		if (gpsMap!=null && !gpsMap.isEmpty()){
			//return  gpsMap;
		}else{//redis无数据，再从数据库取数据,并写入redis
			//taskId=taskId.split("R")[0];//测试用，删除此行
			try {
				//流式查询本地测试查询10w条数据不超过1s,表分区;服务器查询依旧慢
				gpsMap=queryHisGpsList("select id, taskId,taskName, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, `type`, longitude, latitude, lot, lat, speed, direction, color " +
						" from outbound_gpslog where taskId= "+taskId, null, "id,taskId,equip,equipCard,police,prisoner,stauts,errorStatus,uptime,type,longitude,latitude,lot,lat,speed,direction,color".split(","));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		gpsMap.forEach((K,V)->gpsDataList.add(V));
		resMap.put("taskId",taskId);
		resMap.put("taskdata",gpsDataList);
		//写入redis
		//savaRedis(gpsData1);
		return resMap;
	}



	/**
	 * 获取历史任务数据
	 */
	@Override
	public Map<String, Object> getHisGpsData(String taskId) {
		//先从redis取数据
		Map<String, Object> resMap =new HashMap<>();
		Map<String, HisGpsData> gpsMap =new HashMap<>();
		List<HisGpsData>gpsDataList=new ArrayList<>();

		gpsMap =FileUtil.getData2(taskId);
		if (gpsMap!=null && !gpsMap.isEmpty()){
			//return  gpsMap;
		}else {
			//先从redis取数据
			long startTime = System.currentTimeMillis();
			gpsMap = GetData.getHisGpsData(taskId);
			long startTime2 = System.currentTimeMillis();
			System.out.println("redis:" + (startTime2 - startTime));
			if (gpsMap != null && !gpsMap.isEmpty()) {
				//return  gpsMap;
			} else {//redis无数据，再从数据库取数据,并写入redis
				//taskId=taskId.split("R")[0];//测试用，删除此行
				try {
					//流式查询本地测试查询10w条数据不超过1s,表分区;服务器查询依旧慢
					gpsMap = queryHisGpsList("select id, taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, `type`, longitude, latitude, lot, lat, speed, direction, color " +
							" from outbound_gpshislog where taskId= " + taskId, null, "id,taskId,equip,equipCard,police,prisoner,stauts,errorStatus,uptime,type,longitude,latitude,lot,lat,speed,direction,color".split(","));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		/*long startTime3 = System.currentTimeMillis();
		System.out.println("sql:"+(startTime3-startTime2));*/
		gpsMap.forEach((K,V)->gpsDataList.add(V));
		resMap.put("taskId",taskId);
		resMap.put("taskdata",gpsDataList);
		//写入redis
		//savaRedis(gpsData1);
	//	long startTime4 = System.currentTimeMillis();
		//System.out.println("all:"+(startTime4-startTime3));
		return resMap;
	}

	@Override
	public List<Integer> getTaskIdByArea(String areaId) {
		return roadLogDao.getTaskIdByArea(areaId);
	}

	@Override
	public int deleteGpslog() {
		return roadLogDao.deleteGpslog();
	}
	@Override
	public boolean addhisData() {
		return roadLogDao.addhisData();
	}
}
