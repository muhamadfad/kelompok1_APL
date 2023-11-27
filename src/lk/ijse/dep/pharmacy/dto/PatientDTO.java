package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class PatientDTO {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String mobile;
    private String land;
    private String email;
    private String gender;
    private Date birthDate;
    private int age;

    public PatientDTO() {
    }

    public PatientDTO(String id, String nic, String name, String address, String mobile, String land, String email, String gender, Date birthDate, int age) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.land = land;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String   toString() {
        return "PatientDTO{" +
                "id='" + id + '\'' +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", land='" + land + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                '}';
    }
}
