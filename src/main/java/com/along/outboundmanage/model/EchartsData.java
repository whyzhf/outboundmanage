package com.along.outboundmanage.model;

import java.util.Arrays;
import java.util.List;

public class EchartsData {
	private String titleName;//图标名称
	private String[] legendData;//图例
	private int[] xAxisData;//x轴数据
	private List<Series> series;//y轴数值
	public EchartsData() {
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String[] getLegendData() {
		return legendData;
	}

	public void setLegendData(String[] legendData) {
		this.legendData = legendData;
	}

	public int[] getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(int[] xAxisData) {
		this.xAxisData = xAxisData;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "EchartsData{" +
				"titleName='" + titleName + '\'' +
				", legendData=" + Arrays.toString(legendData) +
				", xAxisData=" + Arrays.toString(xAxisData) +
				", series=" + series +
				'}';
	}
}
