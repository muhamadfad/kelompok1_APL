package lk.ijse.dep.pharmacy.util;

import java.util.Date;

public class PatientTM {
    private String nic;
    private String name;
    private String address;
    private String mobile;
    private String regDate;

    public PatientTM() {
    }

    public PatientTM(String nic, String name, String address, String mobile, String regDate) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.regDate = regDate;
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "PatientTM{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}
