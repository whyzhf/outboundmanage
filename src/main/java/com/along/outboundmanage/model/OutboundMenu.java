package  com.along.outboundmanage.model;


import java.util.ArrayList;

public class OutboundMenu {

  private int id;
  private String menuName;//名称
  private int parId;//上级ID
  private int type;//0：目录；1：菜单
  private String url;
  public ArrayList<OutboundMenu> children;
  public OutboundMenu() {
    super();
    this.children = new ArrayList<>();
  }
  public void addChildren(OutboundMenu menu) {
    this.children.add(menu);
  }


  public ArrayList<OutboundMenu> getChildren() {
    return children;
  }

  public void setChildren(ArrayList<OutboundMenu> children) {
    this.children = children;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
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


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "OutboundMenu{" +
            "id=" + id +
            ", menuName='" + menuName + '\'' +
            ", parId=" + parId +
            ", type=" + type +
            ", url='" + url + '\'' +
            ", children=" + children +
            '}';
  }
}
