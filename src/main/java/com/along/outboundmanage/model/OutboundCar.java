package  com.along.outboundmanage.model;


public class OutboundCar {


  private Integer id;

  private String card;//车牌

  private int type;//0：正常，1：故障

  private int status;//0：使用中，1：未使用

  private int areaId;//区域ID


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
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


  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
