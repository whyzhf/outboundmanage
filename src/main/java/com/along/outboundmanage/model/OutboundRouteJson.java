package com.along.outboundmanage.model;


import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;
import static com.along.outboundmanage.utill.GeneralUtils.getJsonArr;
import static com.along.outboundmanage.utill.GeneralUtils.getJsonStr;

public class OutboundRouteJson {

  private int id;
  private String name; //路线名
  private java.sql.Timestamp uptime;
  private String distance;//距离
  private String origin;//出发地
  private String destination;//目的地
  private Object rail;//围栏
  private int areaId;

  @Override
  public String toString() {
    return "OutboundRoute{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", uptime=" + uptime +
            ", distance='" + distance + '\'' +
            ", origin='" + origin + '\'' +
            ", destination='" + destination + '\'' +
            ", rail='" + rail + '\'' +
            ", areaId=" + areaId +
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


  public String getUptime() {

    return uptime==null?null:dateToStr(uptime,"yyyy-MM-dd HH:mm:ss");
  }

  public void setUptime(String uptime) {

    this.uptime =  uptime==null?null:strToSqlDate(uptime,"yyyy-MM-dd HH:mm:ss");
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
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


  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
