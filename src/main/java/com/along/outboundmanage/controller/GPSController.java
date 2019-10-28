package com.along.outboundmanage.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
	@RequestMapping(value = "sendOrder")
	@ResponseBody
	public JSON sendOrder(HttpServletRequest request, @RequestBody Map<String,String> pubParam) {
		String url = "http://120.77.252.208:8080/gps/Order/sendOrder";
		return doPostTestTwo(pubParam, url);
	}

	@RequestMapping(value = "gethisData")
	@ResponseBody
	public JSON gethisData(HttpServletRequest request, @RequestBody Map<String,String> pubParam) {
		String url = "http://120.77.252.208:8080/gps/Order/gethisData";
		return doPostTestTwo(pubParam, url);
	}
}
