package com.along.outboundmanage.model;

import java.util.Arrays;

public class Series {
	private String name;
	private String type;
	private String stack;
	private int[] data;//y轴数值

	@Override
	public String toString() {
		return "Series{" +
				"name='" + name + '\'' +
				", type='" + type + '\'' +
				", stack='" + stack + '\'' +
				", data=" + Arrays.toString(data) +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}
}
