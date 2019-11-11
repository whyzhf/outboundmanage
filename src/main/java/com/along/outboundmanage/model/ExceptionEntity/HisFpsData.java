package com.along.outboundmanage.model.ExceptionEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Set;

public class HisFpsData {
	private String equipCard;
	private Set<JSONObject> data;

	public HisFpsData(String equipCard) {
		this.equipCard = equipCard;
	}

	public HisFpsData(String equipCard, Set<JSONObject> data) {
		this.equipCard = equipCard;
		this.data = data;
	}

	@Override
	public String toString() {
		return "HistData{" +
				"equipCard='" + equipCard + '\'' +
				", data=" + data +
				'}';
	}

	public String getEquipCard() {
		return equipCard;
	}

	public void setEquipCard(String equipCard) {
		this.equipCard = equipCard;
	}

	public Set<JSONObject> getData() {
		return data;
	}

	public void setData(Set<JSONObject> data) {
		this.data = data;
	}
}
