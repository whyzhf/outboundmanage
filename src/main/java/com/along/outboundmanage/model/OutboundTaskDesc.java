package com.along.outboundmanage.model;


import java.sql.Timestamp;
import java.util.List;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;

public class OutboundTaskDesc {

  private int id;
  private String name;//任务名：0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
  private String origin;
  private String destination;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private String describe;//描述
  private int routeId;//路线
  private String routeName;//路线名称
  private int type;
  private int areaId;
  private String  policeId;
  private String  prisonerId;
  private String  carId;

  @Override
  public String toString() {
    return "OutboundTaskDesc{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", origin='" + origin + '\'' +
            ", destination='" + destination + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", describe='" + describe + '\'' +
            ", routeId=" + routeId +
            ", routeName='" + routeName + '\'' +
            ", type=" + type +
            ", areaId=" + areaId +
            ", policeId='" + policeId + '\'' +
            ", prisonerId='" + prisonerId + '\'' +
            ", carId='" + carId + '\'' +
            '}';
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getPoliceId() {
    return policeId;
  }

  public void setPoliceId(String policeId) {
    this.policeId = policeId;
  }

  public String getPrisonerId() {
    return prisonerId;
  }

  public void setPrisonerId(String prisonerId) {
    this.prisonerId = prisonerId;
  }

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
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

    return startTime==null?null:dateToStr(startTime,"yyyy-MM-dd HH:mm:ss");
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

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public int getRouteId() {
    return routeId;
  }

  public void setRouteId(int routeId) {
    this.routeId = routeId;
  }

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }



}
