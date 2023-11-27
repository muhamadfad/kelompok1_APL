package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class Prescription implements SuperEntity {
    private String prescriptionId;
    private Date prescriptionDate;
    private String doctorId;
    private String patientId;

    public Prescription() {
    }

    public Prescription(String prescriptionId, Date prescriptionDate, String doctorId, String patientId) {
        this.prescriptionId = prescriptionId;
        this.prescriptionDate = prescriptionDate;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", prescriptionDate=" + prescriptionDate +
                ", doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                '}';
    }
}
