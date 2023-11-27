package lk.ijse.dep.pharmacy.util;

public class DoctorTM {
    private String nic;
    private String name;
    private String specialization;
    private String mobile;
    private String regHospital;

    public DoctorTM() {
    }

    public DoctorTM(String nic, String name, String specialization, String mobile, String regHospital) {
        this.nic = nic;
        this.name = name;
        this.specialization = specialization;
        this.mobile = mobile;
        this.regHospital = regHospital;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegHospital() {
        return regHospital;
    }

    public void setRegHospital(String regHospital) {
        this.regHospital = regHospital;
    }

    @Override
    public String toString() {
        return "DoctorTM{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", mobile='" + mobile + '\'' +
                ", regHospital='" + regHospital + '\'' +
                '}';
    }
}
