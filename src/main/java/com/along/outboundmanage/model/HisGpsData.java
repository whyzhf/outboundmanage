package com.along.outboundmanage.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class HisGpsData implements Serializable{
	//private static final long serialVersionUID = 8822818790694831649L;
	private Integer taskId;
	private String taskName;
	private String police;
	private String prisoner;
	private String equipCard;
	private String color;
	private List<BigDecimal[]>  gpsData;
	private List<String>  gpsTime;

	public HisGpsData() {
	}

	private HisGpsData(Builder builder) {
		setTaskId(builder.taskId);
		setTaskName(builder.taskName);
		setPolice(builder.police);
		setPrisoner(builder.prisoner);
		setEquipCard(builder.equipCard);
		setColor(builder.color);
		setGpsData(builder.gpsData);
		setGpsTime(builder.gpsTime);
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getEquipCard() {
		return equipCard;
	}

	public void setEquipCard(String equipCard) {
		this.equipCard = equipCard;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<BigDecimal[]> getGpsData() {
		return gpsData;
	}

	public void setGpsData(List<BigDecimal[]> gpsData) {
		this.gpsData = gpsData;
	}

	public List<String> getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(List<String> gpsTime) {
		this.gpsTime = gpsTime;
	}

	public static final class Builder {
		private Integer taskId;
		private String taskName;
		private String police;
		private String prisoner;
		private String equipCard;
		private String color;
		private List<BigDecimal[]> gpsData;
		private List<String> gpsTime;

		public Builder() {
		}

		public Builder taskId(Integer taskId) {
			this.taskId = taskId;
			return this;
		}

		public Builder taskName(String taskName) {
			this.taskName = taskName;
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

		public Builder equipCard(String equipCard) {
			this.equipCard = equipCard;
			return this;
		}

		public Builder color(String color) {
			this.color = color;
			return this;
		}

		public Builder gpsData(List<BigDecimal[]> gpsData) {
			this.gpsData = gpsData;
			return this;
		}

		public Builder gpsTime(List<String> gpsTime) {
			this.gpsTime = gpsTime;
			return this;
		}

		public HisGpsData build() {
			return new HisGpsData(this);
		}
	}
}
