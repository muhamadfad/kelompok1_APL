package lk.ijse.dep.pharmacy.entity;


import java.sql.Date;

public class Patient implements SuperEntity {
    private String patientId;
    private String patientNic;
    private String patientName;
    private String patientAddress;
    private String patientMobile;
    private String patientLand;
    private String patientEmail;
    private String patientGender;
    private Date patientDOB;
    private int patientAge;

    public Patient() {
    }

    public Patient(String patientId, String patientNic, String patientName, String patientAddress, String patientMobile, String patientLand, String patientEmail, String patientGender, Date patientDOB, int patientAge) {
        this.patientId = patientId;
        this.patientNic = patientNic;
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.patientMobile = patientMobile;
        this.patientLand = patientLand;
        this.patientEmail = patientEmail;
        this.patientGender = patientGender;
        this.patientDOB = patientDOB;
        this.patientAge = patientAge;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientNic() {
        return patientNic;
    }

    public void setPatientNic(String patientNic) {
        this.patientNic = patientNic;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getPatientLand() {
        return patientLand;
    }

    public void setPatientLand(String patientLand) {
        this.patientLand = patientLand;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public Date getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(Date patientDOB) {
        this.patientDOB = patientDOB;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", patientNic='" + patientNic + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientAddress='" + patientAddress + '\'' +
                ", patientMobile='" + patientMobile + '\'' +
                ", patientLand='" + patientLand + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientGender='" + patientGender + '\'' +
                ", patientDOB=" + patientDOB +
                ", patientAge=" + patientAge +
                '}';
    }
}
