package lk.ijse.dep.pharmacy.util;

public class PrescriptionInfoTM {
    private String id;
    private String nic;
    private String patientName;
    private String doctorName;
    private String date;
    private double total;

    public PrescriptionInfoTM() {
    }

    public PrescriptionInfoTM(String id, String nic, String patientName, String doctorName, String date, double total) {
        this.id = id;
        this.nic = nic;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.total = total;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PrescriptionInfoTM{" +
                "id='" + id + '\'' +
                ", nic='" + nic + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
