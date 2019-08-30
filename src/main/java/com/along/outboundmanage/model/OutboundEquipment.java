package  com.along.outboundmanage.model;


public class OutboundEquipment {

  private int id;
  private String card;//编号
  private String name;//名称
  private int type;//0：正常，1：故障
  private int status;//0：使用中，1：未使用
  private int form;//0：脚扣，1：遥控器，2：手表
  private int areaId;//区域ID


  public OutboundEquipment() {
  }

  private OutboundEquipment(Builder builder) {
    setId(builder.id);
    setCard(builder.card);
    setName(builder.name);
    setType(builder.type);
    setStatus(builder.status);
    setForm(builder.form);
    setAreaId(builder.areaId);
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

  public static final class Builder {
    private int id;
    private String card;
    private String name;
    private int type;
    private int status;
    private int form;
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

    public Builder type(int type) {
      this.type = type;
      return this;
    }

    public Builder status(int status) {
      this.status = status;
      return this;
    }

    public Builder form(int form) {
      this.form = form;
      return this;
    }

    public Builder areaId(int areaId) {
      this.areaId = areaId;
      return this;
    }

    public OutboundEquipment build() {
      return new OutboundEquipment(this);
    }
  }
}
