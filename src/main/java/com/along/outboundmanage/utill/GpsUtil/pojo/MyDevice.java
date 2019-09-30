package com.along.outboundmanage.utill.GpsUtil.pojo;

import org.java_websocket.client.WebSocketClient;

import java.util.Date;

public class MyDevice {
	private Date newActiveTime;//最新活动时间
	private WebSocketClient client;
	public WebSocketClient getClient() {
		return client;
	}
	public void setClient(WebSocketClient client) {
		this.client = client;
	}
	public Date getNewActiveTime() {
		return newActiveTime;
	}
	public void setNewActiveTime(Date newActiveTime) {
		this.newActiveTime = newActiveTime;
	}

}
