package com.along.outboundmanage.model;

public class Interval {
	private Integer start;
	private  Integer end;

	public Interval(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "Interval{" +
				"start=" + start +
				", end=" + end +
				'}';
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
}
