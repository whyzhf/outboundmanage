package  com.along.outboundmanage.model;


import java.util.ArrayList;

public class OutboundArea {

  private int id;//地域ID
  private String name;//名称
  private String initials;//别名
  private int parId;//上级id
  private int type;//0：地域；1：监所
  private int level;//层级

  public ArrayList<OutboundArea> children;
  public OutboundArea() {
    super();
    this.children = new ArrayList<>();
  }

  private OutboundArea(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setInitials(builder.initials);
    setParId(builder.parId);
    setType(builder.type);
    setLevel(builder.level);
    setChildren(builder.children);
  }

  public String getInitials() {
    return initials;
  }

  public void setInitials(String initials) {
    this.initials = initials;
  }

  public void addChildren(OutboundArea Area) {
    this.children.add(Area);
  }


  public ArrayList<OutboundArea> getChildren() {
    return children;
  }

  public void setChildren(ArrayList<OutboundArea> children) {
    this.children = children;
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


  public int getParId() {
    return parId;
  }

  public void setParId(int parId) {
    this.parId = parId;
  }


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "OutboundArea{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", parId=" + parId +
            ", type=" + type +
            ", level=" + level +
            ", children=" + children +
            '}';
  }


  public static final class Builder {
    private int id;
    private String name;
    private String initials;
    private int parId;
    private int type;
    private int level;
    private ArrayList<OutboundArea> children;

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

    public Builder initials(String initials) {
      this.initials = initials;
      return this;
    }

    public Builder parId(int parId) {
      this.parId = parId;
      return this;
    }

    public Builder type(int type) {
      this.type = type;
      return this;
    }

    public Builder level(int level) {
      this.level = level;
      return this;
    }

    public Builder children(ArrayList<OutboundArea> children) {
      this.children = children;
      return this;
    }

    public OutboundArea build() {
      return new OutboundArea(this);
    }
  }
}
