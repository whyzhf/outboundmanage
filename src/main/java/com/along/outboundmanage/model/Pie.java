package com.along.outboundmanage.model;

public class Pie {
	private int value;
	private String name;

	public Pie() {
	}

	public Pie(int value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Pie{" +
				"value=" + value +
				", name='" + name + '\'' +
				'}';
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
