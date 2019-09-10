package  com.along.outboundmanage.model;


public class OutboundPrisoner {

  private int id;
  private String card;
  private String name;
  private Integer equipmentId;//设备号
  private String equipmentCard;//设备编号
  private int areaId;

  public OutboundPrisoner() {
  }

  private OutboundPrisoner(Builder builder) {
    setId(builder.id);
    setCard(builder.card);
    setName(builder.name);
    setEquipmentId(builder.equipmentId);
    setEquipmentCard(builder.equipmentCard);
    setAreaId(builder.areaId);
  }

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

  public static final class Builder {
    private int id;
    private String card;
    private String name;
    private Integer equipmentId;
    private String equipmentCard;
    private int areaId;

    public Builder() {
    }

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder card(String card) {
      this.card = card;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder equipmentId(Integer equipmentId) {
      this.equipmentId = equipmentId;
      return this;
    }

    public Builder equipmentCard(String equipmentCard) {
      this.equipmentCard = equipmentCard;
      return this;
    }

    public Builder areaId(int areaId) {
      this.areaId = areaId;
      return this;
    }

    public OutboundPrisoner build() {
      return new OutboundPrisoner(this);
    }
  }
}
