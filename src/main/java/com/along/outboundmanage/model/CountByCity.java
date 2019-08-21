package com.along.outboundmanage.model;

public class CountByCity {
	private Integer count;
	private Integer areaId;
	private String name;

	@Override
	public String toString() {
		return "CountByCity{" +
				"count=" + count +
				", areaId=" + areaId +
				", name='" + name + '\'' +
				'}';
	}

	public CountByCity() {
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
