package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class CustomEntity2 {
    private String prescriptionId;
    private String patientNic;
    private String patientName;
    private String doctorName;
    private Date prescriptionDate;
    private double Total;

    public CustomEntity2() {
    }

    public CustomEntity2(String prescriptionId, String patientNic, String patientName, String doctorName, Date prescriptionDate) {
        this.prescriptionId = prescriptionId;
        this.patientNic = patientNic;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
    }

    public CustomEntity2(String prescriptionId, String patientNic, String patientName, String doctorName, Date prescriptionDate, double total) {
        this.prescriptionId = prescriptionId;
        this.patientNic = patientNic;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
        Total = total;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "CustomEntity2{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", patientNic='" + patientNic + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", prescriptionDate=" + prescriptionDate +
                ", Total=" + Total +
                '}';
    }
}
