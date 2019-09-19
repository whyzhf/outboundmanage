package  com.along.outboundmanage.model;


public class OutboundPolice {

  private int id;
  private String card;
  private String name;
  private Integer equipmentId;//遥控器
  private Integer equipmentId2;//手表
  private int areaId;
  private Integer userId;

  public OutboundPolice() {
  }

  private OutboundPolice(Builder builder) {
    setId(builder.id);
    setCard(builder.card);
    setName(builder.name);
    setEquipmentId(builder.equipmentId);
    setEquipmentId2(builder.equipmentId2);
    setAreaId(builder.areaId);
    setUserId(builder.userId);
  }

  @Override
  public String toString() {
    return "OutboundPolice{" +
            "id=" + id +
            ", card='" + card + '\'' +
            ", name='" + name + '\'' +
            ", equipmentId=" + equipmentId +
            ", equipmentId2=" + equipmentId2 +
            ", areaId=" + areaId +
            ", userId=" + userId +
            '}';
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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

  public Integer getEquipmentId2() {
    return equipmentId2;
  }

  public void setEquipmentId2(Integer equipmentId2) {
    this.equipmentId2 = equipmentId2;
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
    private Integer equipmentId2;
    private int areaId;
    private int userId;

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

    public Builder equipmentId2(Integer equipmentId2) {
      this.equipmentId2 = equipmentId2;
      return this;
    }

    public Builder areaId(int areaId) {
      this.areaId = areaId;
      return this;
    }

    public Builder userId(int userId) {
      this.userId = userId;
      return this;
    }

    public OutboundPolice build() {
      return new OutboundPolice(this);
    }
  }
}
