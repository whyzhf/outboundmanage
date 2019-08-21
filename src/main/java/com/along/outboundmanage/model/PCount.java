package com.along.outboundmanage.model;

public class PCount {
	private int coun;
	private  String data;

	@Override
	public String toString() {
		return "PCount{" +
				"coun=" + coun +
				", data='" + data + '\'' +
				'}';
	}

	public int getCoun() {
		return coun;
	}

	public void setCoun(int coun) {
		this.coun = coun;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
