package  com.along.outboundmanage.model;


import java.sql.Timestamp;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;

public class OutboundTask {

  private int id;
  private String name;//任务名：0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
  private String origin;
  private String destination;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private int status;//0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
  private String describe;//描述
  private int routeId;//路线
  private String routeName;//路线名称
  private int type;//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
  private String remarks;//上级审核反馈
  private int areaId;



  public OutboundTask() {
  }

  public OutboundTask(int id, String name, String origin, String destination, Timestamp startTime, Timestamp endTime, String describe, int routeId, int type, int areaId) {
    this.id = id;
    this.name = name;
    this.origin = origin;
    this.destination = destination;
    this.startTime = startTime;
    this.endTime = endTime;
    this.describe = describe;
    this.routeId = routeId;
    this.type = type;
    this.areaId = areaId;
  }

  @Override
  public String toString() {
    return "OutboundTask{" +
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
            '}';
  }

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
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


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }


  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
