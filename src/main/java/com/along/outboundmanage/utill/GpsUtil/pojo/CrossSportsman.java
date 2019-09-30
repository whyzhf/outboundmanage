package com.along.outboundmanage.utill.GpsUtil.pojo;

import java.io.Serializable;

public class CrossSportsman implements Serializable{
	private String id;

	private String no;

	private String name;

	private String nationality;

	private String checkedState;

	private String imgUrl;

	private String sex;

	private String competitionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCheckedState() {
		return checkedState;
	}

	public void setCheckedState(String checkedState) {
		this.checkedState = checkedState;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(String competitionId) {
		this.competitionId = competitionId;
	}

	/**
	 * 自定义属性
	 * 
	 */
	// gps设备数据id
	private String gid;
	// gps设备编号
	private String gno;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGno() {
		return gno;
	}

	public void setGno(String gno) {
		this.gno = gno;
	}
	//gps数据集合
	private CrossGpsData crossGpsData;

	public CrossGpsData getCrossGpsData() {
		return crossGpsData;
	}

	public void setCrossGpsData(CrossGpsData crossGpsData) {
		this.crossGpsData = crossGpsData;
	}

}