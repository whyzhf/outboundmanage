package com.along.outboundmanage.model;


public class OutboundPoliceForSel {

  private int id;
  private String card;
  private String name;
  private int equipmentId;//遥控器
  private int equipmentId2;//手表
  private String equipmentCard;//遥控器编号
  private String equipmentCard2;//手表编号
  private int areaId;
  private int userId;

  @Override
  public String toString() {
    return "OutboundPoliceForSel{" +
            "id=" + id +
            ", card='" + card + '\'' +
            ", name='" + name + '\'' +
            ", equipmentId=" + equipmentId +
            ", equipmentId2=" + equipmentId2 +
            ", equipmentCard='" + equipmentCard + '\'' +
            ", equipmentCard2='" + equipmentCard2 + '\'' +
            ", areaId=" + areaId +
            ", userId=" + userId +
            '}';
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }



  public String getEquipmentCard() {
    return equipmentCard;
  }

  public void setEquipmentCard(String equipmentCard) {
    this.equipmentCard = equipmentCard;
  }

  public String getEquipmentCard2() {
    return equipmentCard2;
  }

  public void setEquipmentCard2(String equipmentCard2) {
    this.equipmentCard2 = equipmentCard2;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(int equipmentId) {
    this.equipmentId = equipmentId;
  }


  public int getEquipmentId2() {
    return equipmentId2;
  }

  public void setEquipmentId2(int equipmentId2) {
    this.equipmentId2 = equipmentId2;
  }


  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
