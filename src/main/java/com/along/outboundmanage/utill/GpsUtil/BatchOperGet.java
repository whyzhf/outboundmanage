package com.along.outboundmanage.utill.GpsUtil;

import java.util.*;

import redis.clients.jedis.*;

import static com.along.outboundmanage.utill.GpsUtil.SysUtil.REDIS_URL;

public class BatchOperGet {

	private static final String HOST = REDIS_URL;
	private static final int PORT = 6379;

	// 批量从Redis中获取数据，正常使用
	public static Map<String, String> batchGetNotUsePipeline() throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		Jedis jedis = new Jedis(HOST, PORT);
		jedis.auth("123456");
		String keyPrefix = "demo2";
		String cursor = "0";
		List<Tuple> list = new ArrayList<>();
		long begin = System.currentTimeMillis();
		while(true){
			ScanResult<Tuple> compid = jedis.zscan(keyPrefix, cursor);
			List<Tuple> result = compid.getResult();
			list.addAll(result);

		//	cursor = compid.getStringCursor();
			cursor = compid.getCursor();
		//	System.out.println(cursor+": "+compid.isCompleteIteration());
			if(compid.isCompleteIteration())
				break;
		}

	//	Set<String> value = jedis.zrange(keyPrefix,0,-1);

		jedis.close();

		long end = System.currentTimeMillis();
		System.out.println("not use pipeline batch get total time：" + (end - begin)+" ## "+list.size());
		return map;
	}

	// 批量从Redis中获取数据，使用Pipeline
	public static Map<String, String> batchGetUsePipeline() throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		Jedis jedis = new Jedis(HOST, PORT);
		jedis.auth("123456");
		Pipeline pipelined = jedis.pipelined();
		String keyPrefix = "demo2";

		// 使用pipeline方式批量获取数据，只能获取到value值，对应的key获取不到，我们通过一个中间map来获取key
		HashMap<String, Response<String>> intrmMap = new HashMap<String, Response<String>>();
		long begin = System.currentTimeMillis();
			//intrmMap.put(key, pipelined.get(key));
		//intrmMap.put(keyPrefix, pipelined.getrange(keyPrefix,0,-1));
		Set<String> set=new HashSet<>();
		Response<Set<String>> getrange = pipelined.zrange(keyPrefix,0,-1);
		pipelined.sync();
		jedis.close();

		set = getrange.get();
		System.out.println("size:"+set.size());

		long end = System.currentTimeMillis();
		System.out.println("use pipeline batch get total time：" + (end - begin));
		return map;
	}

	public static void main(String[] args) {
		try {
			batchGetNotUsePipeline();
			batchGetUsePipeline();
		//	batchGetUsePipeline();
//          Map<String, String> normalMap = batchGetNotUsePipeline();
//          for(Map.Entry<String, String> entry : normalMap.entrySet()) {
//              System.out.println(entry.getKey() + "=" + entry.getValue());
//          }

//          Map<String, String> pipelineMap = batchGetUsePipeline();
//          for(Map.Entry<String, String> entry : pipelineMap.entrySet()) {
//              System.out.println(entry.getKey() + "=" + entry.getValue());
//          }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
