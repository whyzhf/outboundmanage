package com.along.outboundmanage.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EchartsMapData {
	private String name;
	private Map<String,Integer>value;

	@Override
	public String toString() {
		return "EchartsMapData{" +
				"name='" + name + '\'' +
				", value=" + value +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getValue() {
		return value;
	}

	public void setValue(Map<String, Integer> value) {
		this.value = value;
	}
}
