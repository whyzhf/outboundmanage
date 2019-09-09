package  com.along.outboundmanage.model;


public class OutboundUser {

  private int id;//用户id
  private String userName;//用户名
  private String password;//用户密码
  private String trueName;//用户真实名
  private String card;//用户编号
  private Integer areaId;//地域ID
  private Integer roleId;//角色ID

  @Override
  public String toString() {
    return "OutboundUser{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", trueName='" + trueName + '\'' +
            ", card='" + card + '\'' +
            ", areaId=" + areaId +
            ", roleId=" + roleId +
            '}';
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(String trueName) {
    this.trueName = trueName;
  }


  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
  }


  public Integer getAreaId() {
    return areaId;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

}
