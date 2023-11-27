package lk.ijse.dep.pharmacy.dto;

public class SupplierDTO {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String land;
    private String email;
    private String vehicalNo;

    public SupplierDTO(String id, String name, String address, String mobile, String land, String email, String vehicalNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.land = land;
        this.email = email;
        this.vehicalNo = vehicalNo;
    }

    public SupplierDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicalNo() {
        return vehicalNo;
    }

    public void setVehicalNo(String vehicalNo) {
        this.vehicalNo = vehicalNo;
    }

    @Override
    public String toString() {
        return "SupplierDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", land='" + land + '\'' +
                ", email='" + email + '\'' +
                ", vehicalNo='" + vehicalNo + '\'' +
                '}';
    }
}
