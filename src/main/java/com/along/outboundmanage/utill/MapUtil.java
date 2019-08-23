package com.along.outboundmanage.utill;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 地图
 *  key:ea332a8e30bdf60db9402e1ad6493293
 *  待开发
 */
public class MapUtil {
	/**
	 * 根据高德key
	 */
	private static String KEY="ea332a8e30bdf60db9402e1ad6493293";
	/**
	 * 返回值类型
	 */
	private static final String OUTPUT = "JSON";
	/**
	 * 根据地名获取高德经纬度Api
	 */
	private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";
	/**
	 * 根据高德经纬度获取地名Api
	 */
	private static final String GET_ADDRESS_URL = "http://restapi.amap.com/v3/geocode/regeo";

	public static void main(String[] args) {
		System.out.println(getAddressByLonLat(117.020359,36.668530));
		//System.out.println(getLonLarByAddress("山东省"));


	}
	/**
	 * 根据高德经纬度获取地址信息
	 *
	 * @param gdLon 高德地图经度
	 * @param gdLat 高德地图纬度
	 * @return
	 */
	public static String getAddressByLonLat(double gdLon, double gdLat) {

		String location = gdLon + "," + gdLat;
		Map<String, String> params = new HashMap<>();
		params.put("location", location);
		try {
			// 拼装url
			String url = jointUrl(params, OUTPUT, KEY, GET_ADDRESS_URL);
			return HttpClientUtils.doPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据地址信息获取高德经纬度
	 *
	 * @param address 地址信息
	 * @return
	 */
	public static String getLonLarByAddress(String address) {
		Map<String, String> params = new HashMap<>();
		params.put("address", address);

		// Map<String, String> result = new HashMap<>();
		try {
			// 拼装url
			String url = jointUrl(params, OUTPUT, KEY, GET_LNG_LAT_URL);
			// 调用高德地图SDK
			return HttpClientUtils.doPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拼接请求字符串
	 *
	 * @param params
	 * @param output
	 * @param key
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static String jointUrl(Map<String, String> params, String output, String key, String url) throws IOException {
		StringBuilder baseUrl = new StringBuilder();
		baseUrl.append(url);

		int index = 0;
		Set<Map.Entry<String, String>> entrys = params.entrySet();
		for (Map.Entry<String, String> param : entrys) {
			// 判断是否是第一个参数
			if (index == 0) {
				baseUrl.append("?");
			} else {
				baseUrl.append("&");
			}
			baseUrl.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf-8"));
			index++;
		}
		baseUrl.append("&output=").append(output).append("&key=").append(key);

		return baseUrl.toString();
	}


}
