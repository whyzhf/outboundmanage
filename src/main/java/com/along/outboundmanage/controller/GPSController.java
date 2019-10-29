package com.along.outboundmanage.controller;

import com.alibaba.fastjson.JSON;
import com.along.outboundmanage.service.GpsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.along.outboundmanage.utill.HttpClientUtil.doPostTestTwo;

/**
 *gps接口管理
 * @author why
 * @return
 * @description  利用httpClient与gps项目进行交互操作
 */
@RestController
@RequestMapping(value = "Order")
public class GPSController {
	@Resource
	private GpsService gpsService;


	@RequestMapping(value = "sendOrder")
	@ResponseBody
	public JSON sendOrder(HttpServletRequest request, @RequestBody Map<String,String> pubParam) {
		String url = "http://localhost:8085/gps/Order/sendOrder";
		return doPostTestTwo(pubParam, url);
	}

	@RequestMapping(value = "gethisData")
	@ResponseBody
	public  Map<String,Object>  gethisData( @RequestBody Map<String,String> pubParam) {
		//String url = "http://localhost:8085/gps/Order/gethisData";
		//return doPostTestTwo(pubParam, url);
		String taskId=pubParam.get("taskId");
		Map<String,Object> map=new HashMap<>();
		try {
           /* if (resmap.get(taskId)!=null){
                return resmap;
            }*/
			map.put("data",gpsService.getfile(taskId));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}

	}
}
