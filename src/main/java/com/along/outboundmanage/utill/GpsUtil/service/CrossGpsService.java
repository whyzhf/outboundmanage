package com.along.outboundmanage.utill.GpsUtil.service;

import com.alibaba.fastjson.JSONObject;

import com.along.outboundmanage.utill.GpsUtil.pojo.*;
import com.along.outboundmanage.utill.GpsUtil.util.GPSConverterUtils;
import com.along.outboundmanage.utill.GpsUtil.util.LogUtil;
import com.along.outboundmanage.utill.GpsUtil.util.UUIDUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class CrossGpsService implements Runnable {
	//private CrossGpsDao dao = new CrossGpsDao();
	Map<String, MyDevice> driveMap = new ConcurrentHashMap<String, MyDevice>();
	Vector<CrossGpsData> list = new Vector<>();
	//JedisUtil jutil = JedisUtil.getInstance();// jedis工具对象

	/**
	 * 更新数据
	 * 
	 * @param arr
	 */
	public void updateData(String[] arr) {

		// WGS84转换火星坐标
		GPS gps = GPSConverterUtils.gps84_To_Gcj02(Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
		arr[4] = gps.getLat() + "";
		arr[5] = gps.getLon() + "";
		try {
			CompetitionInfo info = getCompetition(arr[0]);
			if (null != info) {
				// 获取设备
				CrossSportsman cs = getSportsman(arr[0], info);
				if (cs == null) {
					String msg = "获取GPS:" + arr[0] + " 未找到绑定人员信息";
					System.out.println(msg);
					LogUtil.writeLog(msg);
					return;
				}
				CrossGpsData gd;
				try {
					gd = getGpsData(arr, info, cs.getId());
					cs.setCrossGpsData(gd);
					list.add(gd);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						MyDevice drive = getDrive(arr[0]);
						// 发送消息

						// 推送给客户端数据
						if (drive != null) {
							if (drive.getClient() != null) {

								// 消息1
								MyMessage message = new MyMessage();
								message.setSportsman(cs);
								message.setUrlCode(
										info.getCompetitionId() + info.getCompetitionItemId() + info.getGroupId());
								String jsonString = JSONObject.toJSONString(message);
								drive.setNewActiveTime(new Date());// 更新最新时间搓
								WebSocketClient client = drive.getClient();
								if (!client.isOpen()) {
									client = getWebSocketClient(arr[0]);
									drive.setClient(client);
								}
								client.send(jsonString);
								// 消息2
								MyMessage message1 = new MyMessage();
								message1.setSportsman(cs);
								message1.setUrlCode(info.getCompetitionId() + info.getCompetitionItemId());
								String jsonString1 = JSONObject.toJSONString(message1);
								drive.setNewActiveTime(new Date());// 更新最新时间搓
								client.send(jsonString1);
							} else {
								String msg = "未获取到客户端...";
								LogUtil.writeLog(msg);
							}
						} else {
							String msg = "未获取到设备...";
							LogUtil.writeLog(msg);
						}
					}
				}.start();
			} else {
				String msg = "获取GPS:" + arr[0] + " 赛事信息为空...";
				System.out.println(msg);
			//	LogUtil.writeLog(msg);
			}

		} finally {

		}
	}

	/**
	 * 生成gps数据实体
	 * 
	 * @param arr
	 * @param info
	 * @return
	 * @throws ParseException
	 */
	private CrossGpsData getGpsData(String arr[], CompetitionInfo info, String sportsmanId) throws ParseException {

		CrossGpsData gd = new CrossGpsData();
		gd.setId(UUIDUtils.getUUID());
		gd.setAltitude(Short.parseShort(arr[6]));
		gd.setCurrTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arr[9]));
		gd.setDirection(Short.parseShort(arr[8]));
		gd.setGpsNo(arr[0]);
		gd.setLat(arr[1]);
		gd.setLot(arr[2]);
		gd.setIsLocation((byte) (arr[3].equals("定位") ? 1 : 0));
		gd.setLatitude(new BigDecimal(arr[4]));
		gd.setLongitude(new BigDecimal(arr[5]));
		gd.setSpeed(Short.parseShort(arr[7]));
		gd.setSportsmanId(sportsmanId);
		gd.setCompetitionId(info.getCompetitionId());
		gd.setGroupId(info.getGroupId());
		gd.setCompetitionItemId(info.getCompetitionItemId());
		return gd;

	}

	/**
	 * 获取设备信息
	 * 
	 * @param gpsNo
	 * @return
	 */
	private MyDevice getDrive(String gpsNo) {
		MyDevice drive = null;
		if (driveMap.get(gpsNo) == null) {
			drive = new MyDevice();
			drive.setNewActiveTime(new Date());
			WebSocketClient webSocketClient = getWebSocketClient(gpsNo);
			drive.setClient(webSocketClient);
			driveMap.put(gpsNo, drive);
		} else {
			drive = driveMap.get(gpsNo);
		}
		return drive;
	}

	/**
	 * 根据urlCode获取websocketClient
	 * 
	 * @param urlCode
	 * @return
	 */
	private WebSocketClient getWebSocketClient(String urlCode) {
		InputStream is = this.getClass().getResourceAsStream("/app.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException ex) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 通知web客户端有数据更新，刷新数据
		//String url = p.getProperty("client.url") + urlCode;
		String url ="ws://120.77.252.208/websocket/" + urlCode;
		WebSocketClient client = null;
		try {
			client = new WebSocketClient(new URI(url), new Draft_6455()) {

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(Exception arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onMessage(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onOpen(ServerHandshake arg0) {
					// TODO Auto-generated method stub

					System.out.println("websocketClient握手成功");

				}

			};
			client.connect();
			Calendar currTime = Calendar.getInstance();
			currTime.setTime(new Date());
			currTime.add(Calendar.SECOND, 10);
			while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
				System.out.println("正在连接...");
				if (new Date().compareTo(currTime.getTime()) > 0) {
					String msg = "连接超时...";
					System.err.println(msg);
					LogUtil.writeLog(msg);
					break;
				}
			}

			// client.close();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 检测设备是否超时
	 */
	private  void checkDrive() {
		Calendar currTime = Calendar.getInstance();
		currTime.setTime(new Date());
		currTime.add(Calendar.MINUTE, -30);
		Iterator<Entry<String, MyDevice>> iterator = driveMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, MyDevice> entity = iterator.next();
			if (entity != null) {
				MyDevice value = entity.getValue();
				System.out.println(new SimpleDateFormat("HH:mm:ss").format(currTime.getTime()) + "=>"
						+ new SimpleDateFormat("HH:mm:ss").format(value.getNewActiveTime()));
				if (currTime.getTime().compareTo(value.getNewActiveTime()) > 0) {
					// 关闭客户端
					value.getClient().close();
				
						iterator.remove();// 移除超时的设备
					System.out.println("移除设备：" + entity.getKey());
				}
			}

		}

	}

	/**
	 * 保存gps数据
	 */
	private  void saveGpsData() {

		if (list.size() > 0) {
			try {
			//	dao.saveGpsData(list);
				Iterator<CrossGpsData> iterator = list.iterator();
				saveGpsDataToRedis();
				while (iterator.hasNext()) {
					if (iterator.next() != null) {
						
							iterator.remove();	
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据gps设备赛项信息，包含缓存
	 * 
	 * @param gpsNo
	 * @return
	 */
	public CompetitionInfo getCompetition(String gpsNo) {
		CompetitionInfo info = null;
		/*
		 * 一般第一次访问的时候先从数据库读取数据，然后将数据写入到缓存，再次访问同一内容的时候就从缓存中读取，如果缓存中没有则从数据库中读取
		 * 所以我们添加缓存逻辑的时候，从数据库中将内容读取出来之后，先set入缓存，然后再从缓存中添加读取行为，如果缓存为空则从数据库中进行读取
		 */
		// 从缓存中获取值
		String key = "competition" + gpsNo;
	//	String jsonStr = jutil.STRINGS.get(key);
		//if (!StringUtils.isBlank(jsonStr)) {
			//info = JSONObject.parseObject(jsonStr, CompetitionInfo.class);
			//jutil.expire(key, 60 * 10);
		//} else {
			try {
				//info = dao.getCompetition(gpsNo);
				// 向缓存中放入值
				String jsonData = JSONObject.toJSONString(info);
			//	jutil.STRINGS.set(key, jsonData);
			//	jutil.expire(key, 60 * 10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		return info;
	}

	/**
	 * 根据gps设备赛项信息，包含缓存
	 * 
	 * @param gpsNo
	 * @return
	 */
	public CrossSportsman getSportsman(String gpsNo, CompetitionInfo info) {
		CrossSportsman s = null;
		/*
		 * 一般第一次访问的时候先从数据库读取数据，然后将数据写入到缓存，再次访问同一内容的时候就从缓存中读取，如果缓存中没有则从数据库中读取
		 * 所以我们添加缓存逻辑的时候，从数据库中将内容读取出来之后，先set入缓存，然后再从缓存中添加读取行为，如果缓存为空则从数据库中进行读取
		 */
		// 从缓存中获取值
		String key = "sportsman" + gpsNo;
		//String jsonStr = jutil.STRINGS.get(key);
		//if (!StringUtils.isBlank(jsonStr)) {
		//	s = JSONObject.parseObject(jsonStr, CrossSportsman.class);
			//jutil.expire(key, 60 * 10);
		//} else {
			try {
				//s = dao.getSportsman(gpsNo, info);
				// 向缓存中放入值
				String jsonData = JSONObject.toJSONString(s);
			//	jutil.STRINGS.set(key, jsonData);
			//	jutil.expire(key, 60 * 10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		return s;
	}

	/**
	 * 缓存redis
	 */
	private void saveGpsDataToRedis() {
		Iterator<CrossGpsData> iterator = list.iterator();
		while (iterator.hasNext()) {
			CrossGpsData next = iterator.next();
			String key =  next.getGpsNo();
			String jsonString = JSONObject.toJSONString(next);
			//jutil.SORTSET.zadd(key, next.getCurrTime().getTime(), jsonString);
			//jutil.expire(key, 60 * 60 * 24 * 7);
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (driveMap) {
				checkDrive();
			}
			synchronized (list) {
				try {
					list.wait(2000);//2秒保存一次数据，并释放当前锁对象
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				saveGpsData();
			}

		}

	}
}
