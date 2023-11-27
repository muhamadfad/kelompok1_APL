package lk.ijse.dep.pharmacy.dto;

public class DoctorDTO {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String mobile;
    private String land;
    private String email;
    private String specialization;
    private String regHospital;

    public DoctorDTO() {
    }

    public DoctorDTO(String id, String nic, String name, String address, String mobile, String land, String email, String specialization, String regHospital) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.land = land;
        this.email = email;
        this.specialization = specialization;
        this.regHospital = regHospital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getRegHospital() {
        return regHospital;
    }

    public void setRegHospital(String regHospital) {
        this.regHospital = regHospital;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id='" + id + '\'' +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", land='" + land + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", regHospital='" + regHospital + '\'' +
                '}';
    }
}
