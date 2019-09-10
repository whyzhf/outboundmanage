package com.along.outboundmanage.model;

import java.sql.Timestamp;
import java.util.Arrays;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;

public class OutboundTaskV2 {
	private int id;
	private String name;//任务名：0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
	private String origin;
	private String destination;
	private java.sql.Timestamp startTime;
	private java.sql.Timestamp endTime;
	private Integer status;//0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
	private String describe;//描述
	private Integer routeId;//路线
	private String routeName;//路线名称
	private Integer type;//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
	private String remarks;//上级审核反馈
	private Integer areaId;
	private String areaName;//地域名
	private String police;//干警
	private String prisoner;//犯人
	private String car;//犯人
	private Integer[]  policeIds;//干警id
	private Integer[]  watchIds;//手表id
	private Integer[]  handsetIds;//遥控id
	private Integer[]  prisonerIds;//犯人id
	private Integer[]  grapplersIds;//脚扣id
	private Integer[]  carIds;//车辆id

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public OutboundTaskV2() {
	}

	@Override
	public String toString() {
		return "OutboundTaskV2{" +
				"id=" + id +
				", name='" + name + '\'' +
				", origin='" + origin + '\'' +
				", destination='" + destination + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", status=" + status +
				", describe='" + describe + '\'' +
				", routeId=" + routeId +
				", routeName='" + routeName + '\'' +
				", type=" + type +
				", remarks='" + remarks + '\'' +
				", areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", police='" + police + '\'' +
				", prisoner='" + prisoner + '\'' +
				", policeIds=" + Arrays.toString(policeIds) +
				", watchIds=" + Arrays.toString(watchIds) +
				", handsetIds=" + Arrays.toString(handsetIds) +
				", prisonerIds=" + Arrays.toString(prisonerIds) +
				", grapplersIds=" + Arrays.toString(grapplersIds) +
				", carIds=" + Arrays.toString(carIds) +
				'}';
	}

	private OutboundTaskV2(Builder builder) {
		setId(builder.id);
		setName(builder.name);
		setOrigin(builder.origin);
		setDestination(builder.destination);
		setStartTime(builder.startTime==null?null:dateToStr(startTime,"yyyy-MM-dd HH:mm:ss"));
		setEndTime(builder.endTime==null?null:dateToStr(endTime,"yyyy-MM-dd HH:mm:ss"));
		setStatus(builder.status);
		setDescribe(builder.describe);
		setRouteId(builder.routeId);
		setRouteName(builder.routeName);
		setType(builder.type);
		setRemarks(builder.remarks);
		setAreaId(builder.areaId);
		setAreaName(builder.areaName);
		setPolice(builder.police);
		setPrisoner(builder.prisoner);
		setPoliceIds(builder.policeIds);
		setWatchIds(builder.watchIds);
		setHandsetIds(builder.handsetIds);
		setPrisonerIds(builder.prisonerIds);
		setGrapplersIds(builder.grapplersIds);
		setCarIds(builder.carIds);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getStartTime() {
		return  startTime==null?null:dateToStr(startTime,"yyyy-MM-dd HH:mm:ss");
	}

	public void setStartTime(String startTime) {

		this.startTime =  startTime==null?null:strToSqlDate(startTime,"yyyy-MM-dd HH:mm:ss");
	}


	public String getEndTime() {
		return  endTime==null?null:dateToStr(endTime,"yyyy-MM-dd HH:mm:ss");
	}

	public void setEndTime(String endTime) {
		this.endTime =  endTime ==null?null:strToSqlDate(endTime,"yyyy-MM-dd HH:mm:ss");
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getPrisoner() {
		return prisoner;
	}

	public void setPrisoner(String prisoner) {
		this.prisoner = prisoner;
	}

	public Integer[] getPoliceIds() {
		return policeIds;
	}

	public void setPoliceIds(Integer[] policeIds) {
		this.policeIds = policeIds;
	}

	public Integer[] getWatchIds() {
		return watchIds;
	}

	public void setWatchIds(Integer[] watchIds) {
		this.watchIds = watchIds;
	}

	public Integer[] getHandsetIds() {
		return handsetIds;
	}

	public void setHandsetIds(Integer[] handsetIds) {
		this.handsetIds = handsetIds;
	}

	public Integer[] getPrisonerIds() {
		return prisonerIds;
	}

	public void setPrisonerIds(Integer[] prisonerIds) {
		this.prisonerIds = prisonerIds;
	}

	public Integer[] getGrapplersIds() {
		return grapplersIds;
	}

	public void setGrapplersIds(Integer[] grapplersIds) {
		this.grapplersIds = grapplersIds;
	}

	public Integer[] getCarIds() {
		return carIds;
	}

	public void setCarIds(Integer[] carIds) {
		this.carIds = carIds;
	}

	public static final class Builder {
		private int id;
		private String name;
		private String origin;
		private String destination;
		private Timestamp startTime;
		private Timestamp endTime;
		private Integer status;
		private String describe;
		private Integer routeId;
		private String routeName;
		private Integer type;
		private String remarks;
		private Integer areaId;
		private String areaName;
		private String police;
		private String prisoner;
		private Integer[] policeIds;
		private Integer[] watchIds;
		private Integer[] handsetIds;
		private Integer[] prisonerIds;
		private Integer[] grapplersIds;
		private Integer[] carIds;

		public Builder() {
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder origin(String origin) {
			this.origin = origin;
			return this;
		}

		public Builder destination(String destination) {
			this.destination = destination;
			return this;
		}

		public Builder startTime(Timestamp startTime) {
			this.startTime = startTime;
			return this;
		}

		public Builder endTime(Timestamp endTime) {
			this.endTime = endTime;
			return this;
		}

		public Builder status(Integer status) {
			this.status = status;
			return this;
		}

		public Builder describe(String describe) {
			this.describe = describe;
			return this;
		}

		public Builder routeId(Integer routeId) {
			this.routeId = routeId;
			return this;
		}

		public Builder routeName(String routeName) {
			this.routeName = routeName;
			return this;
		}

		public Builder type(Integer type) {
			this.type = type;
			return this;
		}

		public Builder remarks(String remarks) {
			this.remarks = remarks;
			return this;
		}

		public Builder areaId(Integer areaId) {
			this.areaId = areaId;
			return this;
		}

		public Builder areaName(String areaName) {
			this.areaName = areaName;
			return this;
		}

		public Builder police(String police) {
			this.police = police;
			return this;
		}

		public Builder prisoner(String prisoner) {
			this.prisoner = prisoner;
			return this;
		}

		public Builder policeIds(Integer[] policeIds) {
			this.policeIds = policeIds;
			return this;
		}

		public Builder watchIds(Integer[] watchIds) {
			this.watchIds = watchIds;
			return this;
		}

		public Builder handsetIds(Integer[] handsetIds) {
			this.handsetIds = handsetIds;
			return this;
		}

		public Builder prisonerIds(Integer[] prisonerIds) {
			this.prisonerIds = prisonerIds;
			return this;
		}

		public Builder grapplersIds(Integer[] grapplersIds) {
			this.grapplersIds = grapplersIds;
			return this;
		}

		public Builder carIds(Integer[] carIds) {
			this.carIds = carIds;
			return this;
		}

		public OutboundTaskV2 build() {
			return new OutboundTaskV2(this);
		}
	}
}
