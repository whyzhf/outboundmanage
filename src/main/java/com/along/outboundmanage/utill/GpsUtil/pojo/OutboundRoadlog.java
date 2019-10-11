package com.along.outboundmanage.utill.GpsUtil.pojo;


import java.math.BigDecimal;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;


public class OutboundRoadlog {

  private int id;
  private int routeId;//路线号
  private int equipmentId;//设备号
  private int type;//0：路线，1：围栏，2：实时
  private BigDecimal longitude;//经度值
  private BigDecimal latitude;//纬度值
  private String lot;//0东经，1西经，
  private String lat;//0：北纬；1：南纬
  private int speed;//速度
  private int direction;//方向
  private int form;//0：线型，1：圆形，2:多边形，3矩形
  private java.sql.Timestamp uptime;
  private int taskId;

  @Override
  public String toString() {
    return "OutboundRoadlog{" +
            "id=" + id +
            ", routeId=" + routeId +
            ", equipmentId=" + equipmentId +
            ", type=" + type +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            ", lot='" + lot + '\'' +
            ", lat='" + lat + '\'' +
            ", speed=" + speed +
            ", direction=" + direction +
            ", form=" + form +
            ", uptime=" + uptime +
            ", taskId=" + taskId +
            '}';
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getRouteId() {
    return routeId;
  }

  public void setRouteId(int routeId) {
    this.routeId = routeId;
  }


  public int getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(int equipmentId) {
    this.equipmentId = equipmentId;
  }


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
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


  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }


  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }


  public int getForm() {
    return form;
  }

  public void setForm(int form) {
    this.form = form;
  }


  public String getUptime() {
    return  uptime==null?null:dateToStr(uptime,"yyyy-MM-dd HH:mm:ss");
  }

  public void setUptime(String startTime) {

    this.uptime =  startTime==null?null:strToSqlDate(startTime,"yyyy-MM-dd HH:mm:ss");
  }

}
