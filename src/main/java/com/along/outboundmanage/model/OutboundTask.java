package  com.along.outboundmanage.model;


import java.sql.Timestamp;

import static com.along.outboundmanage.utill.DataUtil.dateToStr;
import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;

public class OutboundTask {

  private int id;
  private String name;//任务名：0：外出就医，1：指认现场，2：出庭，3投牢，4：审讯，5：其他
  private String origin;
  private String destination;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private Integer status;//0：审核中，1：审核通过，2：审核未通过，3：执行中，4：已完成
  private String describe;//描述
  private Integer routeId;//路线
  private String routeName;//路线名称
  private Integer type;//0：外出就医，1：指认现场，2：出庭，3投牢，4：审讯，5：其他
  private String remarks;//上级审核反馈
  private Integer areaId;
  private String areaName;//地域名

  public OutboundTask(int id, String name, String origin, String destination, Object o, Object o1,
                      String describe, Integer routeId, Integer type, Integer areaId) {
      this.id=id;
      this.name=name;
      this.origin=origin;
      this.destination=destination;
      this.startTime= (Timestamp) o;
      this.endTime= (Timestamp) o1;
      this.describe=describe;
      this.routeId=routeId;
      this.type=type;
      this.areaId=areaId;
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
            ", areaName='" + areaName + '\'' +
            '}';
  }

  public OutboundTask() {
  }



  private OutboundTask(Builder builder) {
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

    public OutboundTask build() {
      return new OutboundTask(this);
    }
  }
}
