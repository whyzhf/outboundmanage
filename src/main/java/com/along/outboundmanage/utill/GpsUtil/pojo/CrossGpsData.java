package com.along.outboundmanage.utill.GpsUtil.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class  CrossGpsData {
    private String id;

    private String gpsNo;

    private String sportsmanId;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Short altitude;

    private Short speed;

    private Short direction;

    private Date currTime;

    private String lot;

    private String lat;

    private String competitionId;

    private Byte isLocation;

    private String groupId;

    private String competitionItemId;


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGpsNo() {
        return gpsNo;
    }

    public void setGpsNo(String gpsNo) {
        this.gpsNo = gpsNo;
    }

    public String getSportsmanId() {
        return sportsmanId;
    }

    public void setSportsmanId(String sportsmanId) {
        this.sportsmanId = sportsmanId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Short getAltitude() {
        return altitude;
    }

    public void setAltitude(Short altitude) {
        this.altitude = altitude;
    }

    public Short getSpeed() {
        return speed;
    }

    public void setSpeed(Short speed) {
        this.speed = speed;
    }

    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    public Date getCurrTime() {
        return currTime;
    }

    public void setCurrTime(Date currTime) {
        this.currTime = currTime;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public Byte getIsLocation() {
        return isLocation;
    }

    public void setIsLocation(Byte isLocation) {
        this.isLocation = isLocation;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCompetitionItemId() {
        return competitionItemId;
    }

    public void setCompetitionItemId(String competitionItemId) {
        this.competitionItemId = competitionItemId;
    }
    
    private CrossSportsman sportsman;

	public CrossSportsman getSportsman() {
		return sportsman;
	}

	public void setSportsman(CrossSportsman sportsman) {
		this.sportsman = sportsman;
	}
}