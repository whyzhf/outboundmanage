package com.along.outboundmanage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.service.GpsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.along.outboundmanage.utill.HttpClientUtil.doPostTestTwo;
import static com.along.outboundmanage.utill.HttpClientUtil.doPostTestTwo2;

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
		String url = "http://120.77.252.208:8080/gps/Order/sendOrder";
		return doPostTestTwo(pubParam, url);
	}

	//不用
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

	@RequestMapping(value = "getHisData")
	@ResponseBody
	public  Result  gethisData2( @RequestBody Map<String,String> pubParam) {
		String taskId=pubParam.get("taskId");
		String areaId=pubParam.get("areaId");
		List<Map<String,Object>> mapList=new ArrayList<>();
		if(!"-1".equals(areaId)){
			List<Integer> taskIdList = gpsService.getTaskIdByArea(areaId);
			taskIdList.forEach(e->{
				mapList.add(gpsService.getHisGpsData(e+""));
			});

		}else{
			mapList.add(gpsService.getHisGpsData(taskId));

		}
		/*new Thread() {
			public void run() {
				//发送虚拟数据
				String url = "http://120.77.252.208:8080/gps/Order/initgpsServer";
				doPostTestTwo2(url);
			}
		}.start();*/

		return ResultGenerator.genSuccessResult(mapList);

	}


	/**
	 * 获取当前任务之前轨迹（中途进入查看轨迹）
	 * @param pubParam
	 * @return
	 */
	@RequestMapping(value = "getCurrentGpsData")
	@ResponseBody
	public Result getGpsData(@RequestBody Map<String,String> pubParam) {
		String taskId=pubParam.get("taskId");
		String areaId=pubParam.get("areaId");
		List<Map<String,Object>> mapList=new ArrayList<>();
		if(!"-1".equals(areaId)){
			List<Integer> taskIdList = gpsService.getTaskIdByArea(areaId);
			taskIdList.forEach(e->{
				mapList.add(gpsService.getGpsData(e+""));
			});

		}else{
			mapList.add(gpsService.getGpsData(taskId));
		}
		System.out.println(JSONObject.toJSONString(mapList));
		return ResultGenerator.genSuccessResult(mapList);

	}
}
