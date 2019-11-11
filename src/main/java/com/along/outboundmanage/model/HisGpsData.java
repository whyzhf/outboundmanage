package com.along.outboundmanage.model;

import java.math.BigDecimal;
import java.util.List;

public class HisGpsData {
	private String police;
	private String prisoner;
	private String equipCard;
	private String color;
	private List<BigDecimal[]>  gpsData;

	public HisGpsData() {
	}

	private HisGpsData(Builder builder) {
		setPolice(builder.police);
		setPrisoner(builder.prisoner);
		setEquipCard(builder.equipCard);
		setColor(builder.color);
		setGpsData(builder.gpsData);
	}

	@Override
	public String toString() {
		return "HisGpsData{" +
				"police='" + police + '\'' +
				", prisoner='" + prisoner + '\'' +
				", equipCard='" + equipCard + '\'' +
				", color='" + color + '\'' +
				", gpsData=" + gpsData +
				'}';
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

	public static final class Builder {
		private String police;
		private String prisoner;
		private String equipCard;
		private String color;
		private List<BigDecimal[]> gpsData;

		public Builder() {
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

		public HisGpsData build() {
			return new HisGpsData(this);
		}
	}
}
