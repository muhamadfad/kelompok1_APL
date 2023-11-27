package lk.ijse.dep.pharmacy.dto;

public class UserDTO {
    private String userId;
    private String userFullName;
    private String userNic;
    private String userRole;
    private String userEmail;
    private String userName;
    private String password;

    public UserDTO(String userId, String userFullName, String userNic, String userRole, String userEmail, String userName, String password) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userNic = userNic;
        this.userRole = userRole;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
    }

    public UserDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserNic() {
        return userNic;
    }

    public void setUserNic(String userNic) {
        this.userNic = userNic;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userNic='" + userNic + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
