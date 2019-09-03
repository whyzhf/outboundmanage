package com.along.outboundmanage.utill;

import com.along.outboundmanage.model.Enclosure.AddJson;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.along.outboundmanage.utill.GeneralUtils.getJsonStr;

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

	/**
	 * 根据高德围栏Api
	 * add: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=用户key -d 'json'
	 * updata: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=patch -d 'json'
	 * select: curl -i -X GET https://restapi.amap.com/v4/geofence/meta?key=用户key
	 * delete: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=delete -d 'json'
	 * start/stop: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=patch -d 'json'
	 * listen: https://restapi.amap.com/v4/geofence/status
	 */
	private static final String ENCLOSURE_URL = "https://restapi.amap.com/v4/geofence/meta";

	public static void main(String[] args) {
		System.out.println(getAddressByLonLat(117.020359,36.668530));
		//System.out.println(getLonLarByAddress("山东省"));

		/*AddJson addJson = new AddJson.Builder().name("demo").center("115.672126,38.817129").radius("1000")
				.enable(true).valid_time("2017-05-19").repeat("Mon,Tues,Wed,Thur,Fri,Sat,Sun")
				.time("00:00,11:59;13:00,20:59").desc("demotest").alert_condition("enter;leave").build();
		System.out.println(addEnclosure(addJson));*/

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
		Map<String, Object> params = new HashMap<>();
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
		Map<String, Object> params = new HashMap<>();
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

	//新增围栏
	public static String addEnclosure(AddJson addJson){
		//参数

		BeanMap beanMap = BeanMap.create(addJson);
		try {
			// 拼装url
			String url = MapUtil.jointUrl(beanMap, OUTPUT, KEY, ENCLOSURE_URL);
			System.out.println(url);
			return HttpClientUtils.doPost(url, beanMap);
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
	private static String jointUrl(Map<String, Object> params, String output, String key, String url) throws IOException {
		StringBuilder baseUrl = new StringBuilder();
		baseUrl.append(url);

		int index = 0;
		Set<Map.Entry<String, Object>> entrys = params.entrySet();
		for (Map.Entry<String, Object> param : entrys) {
			// 判断是否是第一个参数
			if (index == 0) {
				baseUrl.append("?");
			} else {
				baseUrl.append("&");
			}
			if (param.getValue() instanceof Boolean){
				baseUrl.append(param.getKey()).append("=").append(param.getValue());
			}else {
				baseUrl.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue()+"", "utf-8"));
			}

			index++;
		}
		baseUrl.append("&output=").append(output).append("&key=").append(key);

		return baseUrl.toString();
	}


}
