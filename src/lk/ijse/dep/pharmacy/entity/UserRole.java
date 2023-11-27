package lk.ijse.dep.pharmacy.entity;

public class UserRole implements SuperEntity {
    private String usrRoleId;
    private String roleName;

    public UserRole(String usrRoleId, String roleName) {
        this.usrRoleId = usrRoleId;
        this.roleName = roleName;
    }

    public UserRole() {
    }

    public String getUsrRoleId() {
        return usrRoleId;
    }

    public void setUsrRoleId(String usrRoleId) {
        this.usrRoleId = usrRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "usrRoleId='" + usrRoleId + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
