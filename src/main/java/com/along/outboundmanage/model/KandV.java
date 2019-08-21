package com.along.outboundmanage.model;

public class KandV {
	private Integer id;
	private Integer id2;

	public KandV() {
	}

	public KandV(Integer id, Integer id2) {
		this.id = id;
		this.id2 = id2;
	}

	@Override
	public String toString() {
		return "KandV{" +
				"id=" + id +
				", id2=" + id2 +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(Integer id2) {
		this.id2 = id2;
	}
}
