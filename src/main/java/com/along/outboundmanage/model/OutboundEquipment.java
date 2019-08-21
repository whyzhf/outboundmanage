package  com.along.outboundmanage.model;


public class OutboundEquipment {

  private int id;
  private String card;//编号
  private String name;//名称
  private int type;//0：正常，1：故障
  private int status;//0：使用中，1：未使用
  private int form;//0：脚扣，1：遥控器，2：手表
  private int areaId;//区域ID



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


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


  public int getForm() {
    return form;
  }

  public void setForm(int form) {
    this.form = form;
  }


  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
