package  com.along.outboundmanage.model;


public class OutboundRole {

  private int id;
  private String roleName;

  @Override
  public String toString() {
    return "OutboundRole{" +
            "id=" + id +
            ", roleName='" + roleName + '\'' +
            '}';
  }

  public OutboundRole(int id, String roleName) {
    this.id = id;
    this.roleName = roleName;
  }

  public OutboundRole() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}
