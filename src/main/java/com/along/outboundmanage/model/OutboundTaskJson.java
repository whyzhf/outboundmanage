package com.along.outboundmanage.model;


import java.sql.Timestamp;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;
import static com.along.outboundmanage.utill.GeneralUtils.getJsonArr;

public class OutboundTaskJson {

  private int id;
  private String name;//任务名：0：外出就医，1：指认现场，2：出庭，3投牢，4：审讯，5：其他
  private String origin;
  private String destination;
  private String originLngLat;//出发地经纬度
  private String destinationLngLat;//出发地经纬度
  private Timestamp startTime;
  private Timestamp endTime;
  private Integer status;//0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
  private String describe;//描述
  private Integer routeId;//路线
  private String routeName;//路线名称
  private Integer type;//0：外出就医，1：指认现场，2：出庭，3投牢，4：审讯，5：其他
  private String remarks;//上级审核反馈
  private Integer areaId;
  private String areaName;//地域名
  private Object rail;//围栏

  @Override
  public String toString() {
    return "OutboundTaskJson{" +
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
            ", rail=" + rail +
            '}';
  }

  public Object getRail() {
    return rail;
  }

  public void setRail(Object rail) {
    this.rail = getJsonArr(rail+"");
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

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }


  public String getOriginLngLat() {
    return originLngLat;
  }

  public void setOriginLngLat(String originLngLat) {
    this.originLngLat = originLngLat;
  }

  public String getDestinationLngLat() {
    return destinationLngLat;
  }

  public void setDestinationLngLat(String destinationLngLat) {
    this.destinationLngLat = destinationLngLat;
  }
}
