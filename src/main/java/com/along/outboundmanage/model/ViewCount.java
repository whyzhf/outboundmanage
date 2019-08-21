package com.along.outboundmanage.model;

public class ViewCount {
	private Integer type;
	private Integer count;
	private  String data;

	public ViewCount() {
	}

	@Override
	public String toString() {
		return "ViewCount{" +
				"type=" + type +
				", count=" + count +
				", data='" + data + '\'' +
				'}';
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
