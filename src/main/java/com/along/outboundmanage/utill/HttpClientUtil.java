package com.along.outboundmanage.utill;

import com.alibaba.fastjson.JSON;
import org.apache.catalina.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.along.outboundmanage.utill.GeneralUtils.getJson;

public class HttpClientUtil {
	public static void main(String[] args) {
		Map<String,String> pubParam=new HashMap<>();
		pubParam.put("card","10603455553,10603455499");
		pubParam.put("userId","11");
		String url="http://120.77.252.208:8080/gps/Order/sendOrder";
		doPostTestTwo(pubParam,url);
	}

	public static JSON doPostTestTwo( Map<String,String> pubParam,String url) {

		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// 创建Post请求
		HttpPost httpPost = new HttpPost(url);

		// 我这里利用阿里的fastjson，将Object转换为json字符串;
		// (需要导入com.alibaba.fastjson.JSON包)
		String jsonString = JSON.toJSONString(pubParam);

		StringEntity entity = new StringEntity(jsonString, "UTF-8");
		entity.setContentType("application/json");
		entity.setChunked(true);
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
		httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();

			//System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
			//	System.out.println("响应内容长度为:" + responseEntity.getContentLength());
			//	System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			//	System.out.println("响应内容为:" +responseEntity);
				return getJson( EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public static JSON doPostTestTwo2(String url) {

		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// 创建Post请求
		HttpPost httpPost = new HttpPost(url);

		// 我这里利用阿里的fastjson，将Object转换为json字符串;
		// (需要导入com.alibaba.fastjson.JSON包)
		//String jsonString = JSON.toJSONString(pubParam);

		//StringEntity entity = new StringEntity(jsonString, "UTF-8");
		//entity.setContentType("application/json");
		//entity.setChunked(true);
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
		//httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();

			//System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				//	System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				//	System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
				//	System.out.println("响应内容为:" +responseEntity);
				return getJson( EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
