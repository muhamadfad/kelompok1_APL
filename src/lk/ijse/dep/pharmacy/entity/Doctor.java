package lk.ijse.dep.pharmacy.entity;

public class Doctor implements SuperEntity {
    private String doctorId;
    private String doctorNic;
    private String doctorName;
    private String doctorAddress;
    private String doctorMobile;
    private String doctorLand;
    private String doctorEmail;
    private String doctorSpecialization;
    private String doctorRegHospital;

    public Doctor(String doctorId, String doctorNic, String doctorName, String doctorAddress, String doctorMobile, String doctorLand, String doctorEmail, String doctorSpecialization, String doctorRegHospital) {
        this.doctorId = doctorId;
        this.doctorNic = doctorNic;
        this.doctorName = doctorName;
        this.doctorAddress = doctorAddress;
        this.doctorMobile = doctorMobile;
        this.doctorLand = doctorLand;
        this.doctorEmail = doctorEmail;
        this.doctorSpecialization = doctorSpecialization;
        this.doctorRegHospital = doctorRegHospital;
    }

    public Doctor() {
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorNic() {
        return doctorNic;
    }

    public void setDoctorNic(String doctorNic) {
        this.doctorNic = doctorNic;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getDoctorLand() {
        return doctorLand;
    }

    public void setDoctorLand(String doctorLand) {
        this.doctorLand = doctorLand;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorRegHospital() {
        return doctorRegHospital;
    }

    public void setDoctorRegHospital(String doctorRegHospital) {
        this.doctorRegHospital = doctorRegHospital;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", doctorNic='" + doctorNic + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorAddress='" + doctorAddress + '\'' +
                ", doctorMobile='" + doctorMobile + '\'' +
                ", doctorLand='" + doctorLand + '\'' +
                ", doctorEmail='" + doctorEmail + '\'' +
                ", doctorSpecialization='" + doctorSpecialization + '\'' +
                ", doctorRegHospital='" + doctorRegHospital + '\'' +
                '}';
    }
}
