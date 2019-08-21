package com.along.outboundmanage.model;

public class CountTypeByCity {
	private String type;
	private Integer count;
	private Integer areaId;
	private String name;

	@Override
	public String toString() {
		return "CountTypeByCity{" +
				"type=" + type +
				", count=" + count +
				", areaId=" + areaId +
				", name='" + name + '\'' +
				'}';
	}

	public CountTypeByCity() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
