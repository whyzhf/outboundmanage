package  com.along.outboundmanage.model;


public class OutboundPrisoner {

  private int id;
  private String card;
  private String name;
  private Integer equipmentId;//设备号
  private String equipmentCard;//设备编号
  private int areaId;

  @Override
  public String toString() {
    return "OutboundPrisoner{" +
            "id=" + id +
            ", card='" + card + '\'' +
            ", name='" + name + '\'' +
            ", equipmentId=" + equipmentId +
            ", equipmentCard='" + equipmentCard + '\'' +
            ", areaId=" + areaId +
            '}';
  }

  public String getEquipmentCard() {
    return equipmentCard;
  }

  public void setEquipmentCard(String equipmentCard) {
    this.equipmentCard = equipmentCard;
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


  public Integer getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(Integer equipmentId) {
    this.equipmentId = equipmentId;
  }

  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}
