package com.along.outboundmanage.model;

import java.util.Arrays;
import java.util.List;

public class EchartsPieData {
	private String titleName;//图标名称
	private List<Pie> series;//y轴数值
	public EchartsPieData() {
	}

	@Override
	public String toString() {
		return "EchartsPieData{" +
				"titleName='" + titleName + '\'' +
				", series=" + series +
				'}';
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<Pie> getSeries() {
		return series;
	}

	public void setSeries(List<Pie> series) {
		this.series = series;
	}
}
