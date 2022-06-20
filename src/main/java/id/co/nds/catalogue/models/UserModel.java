package id.co.nds.catalogue.models;

public class UserModel {
    private Integer id;
    private String fullname;
    private String roleId;
    private String callNumber;
    private Integer actorId;
    private String recStatus;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCallNumber() {
        return callNumber;
    }
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }
    public Integer getActorId() {
        return actorId;
    }
    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }
    public String getRecStatus() {
        return recStatus;
    }
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

 
}
