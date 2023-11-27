package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class PrescriptionDTO2 {
    private String presId;
    private String patientNic;
    private String patientName;
    private String doctorName;
    private Date presDate;
    private double total;

    public PrescriptionDTO2() {
    }

    public PrescriptionDTO2(String presId, String patientNic, String patientName, String doctorName, Date presDate, double total) {
        this.presId = presId;
        this.patientNic = patientNic;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.presDate = presDate;
        this.total = total;
    }

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getPresDate() {
        return presDate;
    }

    public void setPresDate(Date presDate) {
        this.presDate = presDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO2{" +
                "presId='" + presId + '\'' +
                ", patientNic='" + patientNic + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", presDate=" + presDate +
                ", total=" + total +
                '}';
    }
}
