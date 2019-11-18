package com.along.outboundmanage.utill.GpsUtil;

import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.model.ExceptionEntity.HisFpsData;
import com.along.outboundmanage.model.HisGpsData;
import com.along.outboundmanage.model.WSgpsData;
import com.along.outboundmanage.utill.JedisUtil;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.StringToLong;

public class GetData {
	public static void main(String[] args) {
		getGpsData("5489");
	}
	public static JedisUtil jutil = JedisUtil.getInstance();// jedis工具对象
	//获取正在执行任务数据
	public static Map<String,Object> getGpsData(String taskId ){
		Jedis jedis = JedisUtil.getInstance().getJedis();
		//获取所有该任务的key(设备)
		Set<String> set = jedis.keys(taskId+"*");
		if (set==null||set.isEmpty()){
			return null;
		}
		Map<String,Object> map=new HashMap<>();
		List<HisFpsData> dataList=new ArrayList<>();
		for (String key : set) {
			Set<String> zrange = jedis.zrange(key, 0, -1);
			Set<JSONObject> collect = zrange.stream().map(e -> JSONObject.parseObject(e)).collect(Collectors.toSet());
			dataList.add(new HisFpsData(key.split("_")[1],collect));
		}
		map.put("data",dataList);
		return map;
	}

	//历史回放
	/*public static Map<String,Object> getHisGpsData(String taskId ){
		Jedis jedis = JedisUtil.getInstance().getJedis();
		//获取所有该任务的key(设备)
		Set<String> set = jedis.keys(taskId+"*");
		if (set==null||set.isEmpty()){
			return null;
		}
		List<Map<String,Object>> resList=new ArrayList<>();
		Map<String,Object> resmap=new HashMap<>();
		for (String key : set) {
			//每一个设备对象
			List<BigDecimal[]> gpsDataList=new ArrayList<>();
			Map<String,Object> map=new HashMap<>();
			Set<String> zrange = jedis.zrange(key, 0, -1);
			String first = zrange.stream().findFirst().get();
			WSgpsData FirwSgpsData = new JSONObject().parseObject(first, WSgpsData.class);
			map.put("police",FirwSgpsData.getPolice());
			map.put("prisoner",FirwSgpsData.getPrisoner());
			map.put("equipCard",FirwSgpsData.getEquipCard());
			map.put("color",FirwSgpsData.getColor());
			zrange.forEach(e->{
				WSgpsData wSgpsData = new JSONObject().parseObject(e, WSgpsData.class);
				gpsDataList.add( new BigDecimal[]{wSgpsData.getLongitude(),wSgpsData.getLatitude()});
			});
			map.put("gpsData",gpsDataList);
			resList.add(map);
		}
		resmap.put("data",resList);
		return resmap;
	}*/

	public static Map<String,HisGpsData> getHisGpsData(String taskId ){
		Jedis jedis = JedisUtil.getInstance().getJedis();
		Map<String, HisGpsData> resultMap = new HashMap<>();
		//获取所有该任务的key(设备)
		Set<String> set = jedis.keys(taskId+"*");
		if (set==null||set.isEmpty()){
			return null;
		}
		List<BigDecimal[]>gpslist=null;
		String equipCard=null;
		for (String key : set) {
			//每一个设备对象
			Set<String> zrange = jedis.zrange(key, 0, -1);
			for (String e : zrange) {
				WSgpsData FirwSgpsData = new JSONObject().parseObject(e, WSgpsData.class);
				if(resultMap.get(equipCard=FirwSgpsData.getEquipCard())!=null){
					resultMap.get(equipCard).getGpsData().add(new BigDecimal[]{FirwSgpsData.getLongitude(),FirwSgpsData.getLatitude()});
				}else{//首次加载
					gpslist=new ArrayList<>();
					gpslist.add(new BigDecimal[]{FirwSgpsData.getLongitude(),FirwSgpsData.getLatitude()});
					resultMap.put(FirwSgpsData.getEquipCard(),new HisGpsData.Builder().color(FirwSgpsData.getColor())
							.equipCard(FirwSgpsData.getEquipCard())
							.prisoner(FirwSgpsData.getPrisoner())
							.police(FirwSgpsData.getPolice())
							.gpsData(gpslist).build()
					);
				}
			}
		}
		return resultMap;
	}


	//从数据库获取值
	public static void getDataByCache(){

	}

	//从数据库获取值
	public static void getDataBySql(){

	}

    //写入redis
    public static void savaRedis(List<WSgpsData> gpsData) {
		//写入redis
		new Thread(() -> {
			gpsData.forEach(e -> {
				String key = e.getTaskId() + "_" + e.getEquipCard();
				double score = StringToLong(e.getUptime(), "yyyy-MM-dd HH:mm:ss").doubleValue();
				jutil.SORTSET.zadd(key, score, JSONObject.toJSONString(e));
				jutil.expire(key, 60 * 60 * 24 * 7);
			});
		}).start();
	}
}
